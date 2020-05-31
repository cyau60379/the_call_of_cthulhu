import rsa


def get_private_key(key_path):
    """
    Function to load the private key of this web server
    :return: the private key
    """
    try:
        with open(key_path, mode='rb') as file:
            data = file.read()
        try:
            private_key = rsa.PrivateKey.load_pkcs1(data)
            return private_key
        except ValueError:
            return None
    except FileNotFoundError:
        return None


def get_public_key(key_path):
    """
    Function to load the public key of this web server
    :return: the public key
    """
    try:
        with open(key_path, mode='rb') as file:
            data = file.read()
        try:
            public_key = rsa.PublicKey.load_pkcs1(data)
            return public_key
        except ValueError:
            return None
    except FileNotFoundError:
        return None
