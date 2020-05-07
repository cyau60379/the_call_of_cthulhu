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
        creature_name = message_json['name']
        author_name = message_json['author']
        book_name = message_json['book']
        affiliation = message_json['affiliation']
        creature_list = Creature.objects.values('name',
                                                'description',
                                                'image_link',
                                                'authorcreature__author__first_name',
                                                'authorcreature__author__surname',
                                                'creaturebook__book__name',
                                                'creaturebook__book__year_of_creation',
                                                'affiliation__name').filter(name=creature_name)
        response_list = []
        for creature in creature_list:
            json_list = {'name': creature['name'],
                         'description': creature['description'],
                         'image': creature['image_link'],
                         'author': creature['authorcreature__author__first_name'] + " "
                         + creature['authorcreature__author__surname'],
                         'book': creature['creaturebook__book__name'],
                         'year': creature['creaturebook__book__year_of_creation'],
                         'affiliation': creature['affiliation__name']}
            response_list.append(json_list)
        response_json = encrypt_data(json.dumps(response_list))
        return JsonResponse(response_json)
