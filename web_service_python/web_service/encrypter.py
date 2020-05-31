import base64
import json
from web_service.KeyLoader import *

PUBLIC_KEY_SPRING = get_public_key('web_service/static/public_key_Spring.pem')
PRIVATE_KEY = get_private_key('web_service/static/private_key_Django.pem')


def verify_authenticity(json_request):
    """
    Function to verify the authenticity of the request
    :param json_request: the JSON sent by the other web server
    :return: the message body of the JSON
    """
    try:
        encrypted_message = base64.b64decode(json_request['message'].encode())
        signature = base64.b64decode(json_request['signature'].encode())
        try:
            message = rsa.decrypt(encrypted_message, PRIVATE_KEY)
            is_good = rsa.verify(message, signature, PUBLIC_KEY_SPRING)
            if is_good:
                return message.decode()
            else:
                return "No"
        except (rsa.pkcs1.DecryptionError, rsa.pkcs1.VerificationError):
            print("Decryption failed")
            return None
    except (AttributeError, KeyError):
        print("Key problem detected")
        return None


def encrypt_data(message):
    """
    Function to encrypt data before sending it
    :param message: the json message to be sent
    :return: the message and the signature for each part of the message
    """

    new_message = json.dumps(message).encode()
    message_list = [new_message[i:i+200] for i in range(0, len(new_message), 200)]
    response_list = []
    signature_list = []
    try:
        for byte in message_list:
            response_body = rsa.encrypt(byte, PUBLIC_KEY_SPRING)
            signature = rsa.sign(response_body, PRIVATE_KEY, "SHA-256")
            response_list.append(base64.b64encode(response_body).decode())
            signature_list.append(base64.b64encode(signature).decode())
        return response_list, signature_list
    except AttributeError:
        print("Key problem detected")
        return None, None
