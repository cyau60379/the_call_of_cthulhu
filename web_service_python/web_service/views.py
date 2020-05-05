import base64
import json
from django.http import JsonResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from web_service.KeyLoader import *


@csrf_exempt
def creature_search(request):
    public_key = get_public_key()
    private_key = get_private_key()
    message = base64.b64decode(request.body)
    decrypted_m = rsa.decrypt(message, private_key).decode()
    print(decrypted_m)
    return JsonResponse(json.loads(decrypted_m))
