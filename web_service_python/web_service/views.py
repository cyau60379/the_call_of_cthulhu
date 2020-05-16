import json
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from web_service.encrypter import *
from web_service.models import *


@csrf_exempt
def creature_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/creatureSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    json_request = json.loads(request.body.decode())  # transform string to JSON
    message_body = verify_authenticity(json_request)  # verify the signature of the message
    if message_body == "No":
        error_message, error_signature = encrypt_data('error')
        error = {'message': error_message, 'signature': error_signature}
        return JsonResponse(error)  # send to the other web service the error response
    else:
        message_json = json.loads(message_body)
        search_name = message_json['name']
        search_type = message_json['searchType']
        creature_object_list = Creature.objects.values('name',
                                                       'description',
                                                       'image_link',
                                                       'authorcreature__author__first_name',
                                                       'authorcreature__author__surname',
                                                       'creaturebook__book__name',
                                                       'creaturebook__book__year_of_creation',
                                                       'affiliation__name')
        # List of objects from the DB with the chosen columns
        # (the lines like authorcreature__author__first_name make the JOIN automatically)

        if search_type == "creature":
            creature_list = creature_object_list.filter(name=search_name)  # filter == WHERE in SQL
        elif search_type == "author":
            author_name = search_name.split(" ")
            creature_list = creature_object_list.filter(authorcreature__author__surname=author_name[-1])
        elif search_type == "book":
            creature_list = creature_object_list.filter(creaturebook__book__name=search_name)
        elif search_type == "affiliation":
            creature_list = creature_object_list.filter(affiliation__name=search_name)
        else:
            creature_list = []

        # for each JSON from the query, simplify the key, encrypt the message and create the signature for it
        creature_json = []
        for creature in creature_list:
            creature_json.append({'name': creature['name'],
                                  'description': creature['description'],
                                  'image': creature['image_link'],
                                  'author': creature['authorcreature__author__first_name'] + " "
                                            + creature['authorcreature__author__surname'],
                                  'book': creature['creaturebook__book__name'],
                                  'year': creature['creaturebook__book__year_of_creation'],
                                  'affiliation': creature['affiliation__name']})

        response_list, signature_list = encrypt_data(creature_json)
        response_json = {'message': response_list, 'signature': signature_list}
        return JsonResponse(response_json)  # send the JSON to the other web service


@csrf_exempt
def author_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/authorSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    json_request = json.loads(request.body.decode())  # transform string to JSON
    message_body = verify_authenticity(json_request)  # verify the signature of the message
    if message_body == "No":
        error_message, error_signature = encrypt_data('error')
        error = {'message': error_message, 'signature': error_signature}
        return JsonResponse(error)  # send to the other web service the error response
    else:
        message_json = json.loads(message_body)
        search_name = message_json['name']
        search_type = message_json['searchType']
        author_object_list = Author.objects.values('first_name', 'surname', 'image_link', 'birth_date', 'death_date')
        # List of objects from the DB with the chosen columns

        if search_type == "first_name":
            author_list = author_object_list.filter(first_name=search_name)  # filter == WHERE in SQL
        elif search_type == "surname":
            author_list = author_object_list.filter(surname=search_name)
        elif search_type == "birth":
            author_list = author_object_list.filter(birth_date=search_name)
        elif search_type == "death":
            author_list = author_object_list.filter(death_date=search_name)
        else:
            author_list = []

        # for each JSON from the query, simplify the key, encrypt the message and create the signature for it
        author_json = []
        for author in author_list:
            books = Book.objects.values('name',
                                        'year_of_creation',
                                        'authorbook__author__surname') \
                .filter(authorbook__author__surname=author['surname'])
            creatures = Creature.objects.values('name', 'authorcreature__author__surname') \
                .filter(authorcreature__author__surname=author['surname'])

            book_list = []
            creature_list = []
            for book in books:
                book_info = book['name'] + " (" + str(book['year_of_creation']) + ")"
                if book_info not in book_list:
                    book_list.append(book_info)
            for creature in creatures:
                if creature['name'] not in creature_list:
                    creature_list.append(creature['name'])

            author_json.append({'name': author['first_name'] + " " + author['surname'],
                                'date': "(" + str(author['birth_date']) + " - "
                                        + str(author['death_date']) + ")",
                                'image': author['image_link'],
                                'book': book_list,
                                'creature': creature_list})

        response_list, signature_list = encrypt_data(author_json)
        response_json = {'message': response_list, 'signature': signature_list}
        return JsonResponse(response_json)  # send the JSON to the other web service


@csrf_exempt
def book_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/bookSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    json_request = json.loads(request.body.decode())  # transform string to JSON
    message_body = verify_authenticity(json_request)  # verify the signature of the message
    if message_body == "No":
        error_message, error_signature = encrypt_data('error')
        error = {'message': error_message, 'signature': error_signature}
        return JsonResponse(error)  # send to the other web service the error response
    else:
        message_json = json.loads(message_body)
        search_name = message_json['name']
        search_type = message_json['searchType']
        book_object_list = Book.objects.values('name', 'year_of_creation', 'image_link', 'authorbook__author__surname')
        # List of objects from the DB with the chosen columns

        if search_type == "name":
            book_list = book_object_list.filter(name=search_name)  # filter == WHERE in SQL
        elif search_type == "year":
            book_list = book_object_list.filter(year_of_creation=search_name)
        elif search_type == "author":
            author_name = search_name.split(" ")
            book_list = book_object_list.filter(authorbook__author__surname=author_name[-1])
        else:
            book_list = []

        # for each JSON from the query, simplify the key, encrypt the message and create the signature for it
        book_json = []
        for book in book_list:
            authors = Author.objects.values('first_name',
                                            'surname',
                                            'birth_date',
                                            'death_date').filter(authorbook__book__name=book['name'])
            creatures = Creature.objects.values('name').filter(creaturebook__book__name=book['name'])

            author_list = []
            creature_list = []
            for author in authors:
                author_info = author['first_name'] + " " + author['surname'] \
                            + " (" + str(author['birth_date']) + " - " + str(author['death_date']) + ")"
                if author_info not in author_list:
                    author_list.append(author_info)
            for creature in creatures:
                if creature['name'] not in creature_list:
                    creature_list.append(creature['name'])

            book_json.append({'name': book['name'],
                              'year': "(" + str(book['year_of_creation']) + ")",
                              'image': book['image_link'],
                              'author': author_list,
                              'creature': creature_list})

        response_list, signature_list = encrypt_data(book_json)
        response_json = {'message': response_list, 'signature': signature_list}
        return JsonResponse(response_json)  # send the JSON to the other web service


@csrf_exempt
def affiliation_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/affiliationSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    json_request = json.loads(request.body.decode())  # transform string to JSON
    message_body = verify_authenticity(json_request)  # verify the signature of the message
    if message_body == "No":
        error_message, error_signature = encrypt_data('error')
        error = {'message': error_message, 'signature': error_signature}
        return JsonResponse(error)  # send to the other web service the error response
    else:
        message_json = json.loads(message_body)
        search_name = message_json['name']
        search_type = message_json['searchType']
        affiliation_object_list = Affiliation.objects.values('name')
        # List of objects from the DB with the chosen columns

        if search_type == "name":
            affiliation_list = affiliation_object_list.filter(name=search_name)  # filter == WHERE in SQL
        elif search_type == "creature":
            affiliation_list = Creature.objects.values('affiliation__name').filter(name=search_name)
            for i in range(len(affiliation_list)):
                affiliation_list[i]['name'] = affiliation_list[i]['affiliation__name']
        else:
            affiliation_list = []

        # for each JSON from the query, simplify the key, encrypt the message and create the signature for it
        affiliation_json = []
        for affiliation in affiliation_list:
            creatures = Creature.objects.values('name').filter(affiliation__name=affiliation['name'])
            creature_list = []
            for creature in creatures:
                if creature['name'] not in creature_list:
                    creature_list.append(creature['name'])

            affiliation_json.append({'name': affiliation['name'], 'creature': creature_list})

        response_list, signature_list = encrypt_data(affiliation_json)
        response_json = {'message': response_list, 'signature': signature_list}
        return JsonResponse(response_json)  # send the JSON to the other web service
