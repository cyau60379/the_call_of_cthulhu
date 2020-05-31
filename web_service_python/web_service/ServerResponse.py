from django.http import JsonResponse
from web_service.encrypter import *


def response(request, arrangement_method):
    if request.method != "POST":
        return error('Method not allowed', 405)
    else:
        try:
            json_request = json.loads(request.body.decode())  # transform string to JSON
            message_body = verify_authenticity(json_request)  # verify the signature of the message
            if message_body == "No":
                return error('Not acceptable', 406)
            elif message_body is None:
                return error('Service Unavailable', 503)
            else:
                # for each JSON from the query, simplify the key, encrypt the message and create the signature for it
                entity_json = arrangement_method(message_body)
                response_list, signature_list = encrypt_data(entity_json)
                if (response_list is None) and (signature_list is None):
                    return error('Service Unavailable', 503)
                else:
                    response_json = {'message': response_list, 'signature': signature_list}
                    return JsonResponse(response_json, status=200)  # send the JSON to the other web service
        except json.JSONDecodeError:
            return error('Not acceptable', 406)


def error(message, status):
    error_json = {'error': message, 'status': status}
    return JsonResponse(error_json, status=status)
