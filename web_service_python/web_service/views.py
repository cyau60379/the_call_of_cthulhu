import json
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from web_service.encrypter import *
from web_service.models import *


@csrf_exempt
def creature_search(request):
    json_request = json.loads(request.body.decode())
    message_body = verify_authenticity(json_request)
    if message_body == "No":
        error = {'message': 'error'}
        return JsonResponse(error)
    else:
        message_json = json.loads(message_body)
        search_name = message_json['name']
        search_type = message_json['searchType']
        response_list = []
        signature_list = []
        creature_object_list = Creature.objects.values('name',
                                                       'description',
                                                       'image_link',
                                                       'authorcreature__author__first_name',
                                                       'authorcreature__author__surname',
                                                       'creaturebook__book__name',
                                                       'creaturebook__book__year_of_creation',
                                                       'affiliation__name')
        if search_type == "creature":
            creature_list = creature_object_list.filter(name=search_name)
        elif search_type == "author":
            author_name = search_name.split(" ")
            creature_list = creature_object_list.filter(authorcreature__author__surname=author_name[-1])
        elif search_type == "book":
            creature_list = creature_object_list.filter(creaturebook__book__name=search_name)
        elif search_type == "affiliation":
            creature_list = creature_object_list.filter(affiliation__name=search_name)
        else:
            creature_list = []

        for creature in creature_list:
            message, signature = encrypt_data({'name': creature['name'],
                                               'description': creature['description'],
                                               'image': creature['image_link'],
                                               'author': creature['authorcreature__author__first_name'] + " "
                                               + creature['authorcreature__author__surname'],
                                               'book': creature['creaturebook__book__name'],
                                               'year': creature['creaturebook__book__year_of_creation'],
                                               'affiliation': creature['affiliation__name']})
            response_list.append(message)
            signature_list.append(signature)
        print("response: ", response_list)
        response_json = {'message': response_list, 'signature': signature_list}
        return JsonResponse(response_json)
