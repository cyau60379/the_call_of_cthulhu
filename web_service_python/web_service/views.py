from django.views.decorators.csrf import csrf_exempt
from web_service.DataConstruction import *
from web_service.ServerResponse import response


@csrf_exempt
def creature_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/creatureSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    return response(request, arrange_creature_data)


@csrf_exempt
def author_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/authorSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    return response(request, arrange_author_data)


@csrf_exempt
def book_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/bookSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    return response(request, arrange_book_data)


@csrf_exempt
def affiliation_search(request):
    """
    Function executed when the URL is [ip or URL]/web_service/affiliationSearch
    :param request: the POST request sent by the other web service
    :return: the JSON response which will be sent to the other web_service
    """
    return response(request, arrange_affiliation_data)
