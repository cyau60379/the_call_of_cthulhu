import rsa


def get_private_key():
    with open('web_service/static/private_key_Django.pem', mode='rb') as file:
        data = file.read()
    private_key = rsa.PrivateKey.load_pkcs1(data)
    return private_key


def get_public_key():
    with open('web_service/static/public_key_Spring.pem', mode='rb') as file:
        data = file.read()
    public_key = rsa.PublicKey.load_pkcs1(data)
    return public_key
