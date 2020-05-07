import base64
from web_service.KeyLoader import *

PUBLIC_KEY_SPRING = get_public_key()
PRIVATE_KEY = get_private_key()


def verify_authenticity(json_request):
    """
    Function to verify the authenticity of the request
    :param json_request: the JSON sent by the other web server
    :return: the message body of the JSON
    """
    encrypted_message = base64.b64decode(json_request['message'].encode())
    signature = base64.b64decode(json_request['signature'].encode())
    try:
        message = rsa.decrypt(encrypted_message, PRIVATE_KEY)
        is_good = rsa.verify(message, signature, PUBLIC_KEY_SPRING)
        if is_good:
            return message.decode()
        else:
            return "No"
    except Exception:
        return "No"


def encrypt_data(message):
    """
    Function to encrypt data before sending it
    :param message: the json message to be sent
    :return: the JSON which will be sent to the other web server
    """
    new_message = message.encode()
    response_body = rsa.encrypt(new_message, PUBLIC_KEY_SPRING)
    hash_message = rsa.compute_hash(new_message, 'SHA-256')
    signature = rsa.sign(hash_message, PRIVATE_KEY, "SHA-256")
    json_response = {'message': base64.b64encode(response_body).decode(),
                     'signature': base64.b64encode(signature).decode()}
    return json_response
