from django.test import TestCase
from web_service.KeyLoader import *
import web_service.encrypter as wse
from web_service.DataConstruction import *
import json

# ============================================= PARAMETERS

url_list = ['/web_service/creatureSearch', '/web_service/bookSearch', '/web_service/authorSearch',
            '/web_service/affiliationSearch']

creature_param_list = [("Cthulhu", "creature"), ("Lovecraft", "author"), ("The Call of Cthulhu", "book"),
                       ("Great Old Ones", "affiliation")]

author_param_list = [("Howard Phillips", "first_name"), ("Lovecraft", "surname"), (1890, "birth"), (1937, "death")]

book_param_list = [("The Call of Cthulhu", "book"), ("Lovecraft", "author"), (1926, "year")]

affiliation_param_list = [("Great Old Ones", "name"), ("Cthulhu", "creature")]

functions_list = [arrange_creature_data, arrange_book_data, arrange_author_data, arrange_affiliation_data]


class WebServiceTests(TestCase):

    def setUp(self):
        affiliation = Affiliation(name="Great Old Ones")
        affiliation.save()
        author = Author(first_name="Howard Phillips", surname="Lovecraft", birth_date=1890, death_date=1937,
                        image_link='h')
        author.save()
        creature = Creature(name='Cthulhu', description="Master of R'lyeh", image_link="h", affiliation=affiliation)
        creature.save()
        book = Book(name="The Call of Cthulhu", image_link="h", year_of_creation=1926)
        book.save()
        author_book = AuthorBook(book=book, author=author)
        author_book.save()
        author_creature = AuthorCreature(author=author, creature=creature)
        author_creature.save()
        book_creature = CreatureBook(book=book, creature=creature)
        book_creature.save()

    def test_get_public_key(self):
        key = get_public_key('web_service/static/public_key_Spring.pem')
        self.assertFalse(key is None)

    def test_get_public_key_error(self):
        key = get_public_key('web_service/statics/peublic_key_Spring.pem')
        self.assertTrue(key is None)

    def test_get_public_key_with_private(self):
        key = get_public_key('web_service/static/private_key_Django.pem')
        self.assertTrue(key is None)

    def test_get_private_key(self):
        key = get_private_key('web_service/static/private_key_Django.pem')
        self.assertFalse(key is None)

    def test_get_private_key_empty(self):
        key = get_private_key('')
        self.assertTrue(key is None)

    def test_get_private_key_error(self):
        key = get_private_key('web_service/statdzic/privasdte_key_Django.pem')
        self.assertTrue(key is None)

    def test_get_private_key_with_public(self):
        key = get_private_key('web_service/static/public_key_Spring.pem')
        self.assertTrue(key is None)

    def test_verify_authenticity(self):
        request_json = {"signature": "S7m8jELzdE+ZjJAXNfIiJNAgUKIQqnOskA9QtNmBPxESfN8f3fdSDYccEiwAuMSlfNMWC"
                                     "+EcnlO3xwVzP71lDk4QMUgjwQz+G2U35/ZbDIpSwAS5xzm0980ICkt"
                                     "/SZyGSBOO9fRixYgjGBuJZiXVWpXzKISz7h1BXzhhKRyJqKTuuTLQHPJHINLRfLIseE/8"
                                     "+xIuo8oZ2lXee59iMnKiGO0KKcdHBG4y8siFFuNh+Mp7NuHypvDtqkvCmL0PmOy7VOHysW"
                                     "/7YgRlWPxc3uLzqzrYe1IP5EXrzNWZiN179jlx+NG1ymva42Idh+NwRvRyiekZXU5wXxDlTSd8B/JwWw"
                                     "==",
                        "message": "HE/Sv6SjwaBeG2lI1vqj2niTnGZ2TC/i9nPwH+OwvjP/vF2DMDLNLNUwY5ak2bz0yyKLanZ4hyjN1"
                                   "/K9tuggzYWTafKK/TGDe5o+l6cDSK5K"
                                   "+SaPwpVKFkp3OVWYV0rh0WAznJkQYThcxic6d5uAq4iH85vebTwaaSXJstSSQAks0d0vUpBUeQ"
                                   "/RAT9VD2iUsvZyMJZCRz2bhhg88iqu+V1pbg0o//fmeR2gRBxLBM"
                                   "+FsIeibCk5tUyxR7e3uargIWSGrLu5pt"
                                   "+dbEmJsZ0QPHeQ5gdRDnquChUdSxFIvfVkKahtIj6I6nZ9FnIVVGlnSN0sKg9YYxAB1tLVJipXnQ=="}
        response = wse.verify_authenticity(request_json)
        true_request = {"name": "Cthulhu", "searchType": "creature"}
        self.assertEquals(json.loads(response), true_request)

    def test_verify_authenticity_wrong_private_key(self):
        key = wse.PRIVATE_KEY
        wse.PRIVATE_KEY = None
        request_json = {"signature": "S7m8jELzdE+ZjJAXNfIiJNAgUKIQqnOskA9QtNmBPxESfN8f3fdSDYccEiwAuMSlfNMWC"
                                     "+EcnlO3xwVzP71lDk4QMUgjwQz+G2U35/ZbDIpSwAS5xzm0980ICkt"
                                     "/SZyGSBOO9fRixYgjGBuJZiXVWpXzKISz7h1BXzhhKRyJqKTuuTLQHPJHINLRfLIseE/8"
                                     "+xIuo8oZ2lXee59iMnKiGO0KKcdHBG4y8siFFuNh+Mp7NuHypvDtqkvCmL0PmOy7VOHysW"
                                     "/7YgRlWPxc3uLzqzrYe1IP5EXrzNWZiN179jlx+NG1ymva42Idh+NwRvRyiekZXU5wXxDlTSd8B/JwWw"
                                     "==",
                        "message": "HE/Sv6SjwaBeG2lI1vqj2niTnGZ2TC/i9nPwH+OwvjP/vF2DMDLNLNUwY5ak2bz0yyKLanZ4hyjN1"
                                   "/K9tuggzYWTafKK/TGDe5o+l6cDSK5K"
                                   "+SaPwpVKFkp3OVWYV0rh0WAznJkQYThcxic6d5uAq4iH85vebTwaaSXJstSSQAks0d0vUpBUeQ"
                                   "/RAT9VD2iUsvZyMJZCRz2bhhg88iqu+V1pbg0o//fmeR2gRBxLBM"
                                   "+FsIeibCk5tUyxR7e3uargIWSGrLu5pt"
                                   "+dbEmJsZ0QPHeQ5gdRDnquChUdSxFIvfVkKahtIj6I6nZ9FnIVVGlnSN0sKg9YYxAB1tLVJipXnQ=="}
        response = wse.verify_authenticity(request_json)
        wse.PRIVATE_KEY = key
        self.assertEquals(response, None)

    def test_verify_authenticity_wrong_public_key(self):
        key = wse.PUBLIC_KEY_SPRING
        wse.PUBLIC_KEY_SPRING = None
        request_json = {"signature": "S7m8jELzdE+ZjJAXNfIiJNAgUKIQqnOskA9QtNmBPxESfN8f3fdSDYccEiwAuMSlfNMWC"
                                     "+EcnlO3xwVzP71lDk4QMUgjwQz+G2U35/ZbDIpSwAS5xzm0980ICkt"
                                     "/SZyGSBOO9fRixYgjGBuJZiXVWpXzKISz7h1BXzhhKRyJqKTuuTLQHPJHINLRfLIseE/8"
                                     "+xIuo8oZ2lXee59iMnKiGO0KKcdHBG4y8siFFuNh+Mp7NuHypvDtqkvCmL0PmOy7VOHysW"
                                     "/7YgRlWPxc3uLzqzrYe1IP5EXrzNWZiN179jlx+NG1ymva42Idh+NwRvRyiekZXU5wXxDlTSd8B/JwWw"
                                     "==",
                        "message": "HE/Sv6SjwaBeG2lI1vqj2niTnGZ2TC/i9nPwH+OwvjP/vF2DMDLNLNUwY5ak2bz0yyKLanZ4hyjN1"
                                   "/K9tuggzYWTafKK/TGDe5o+l6cDSK5K"
                                   "+SaPwpVKFkp3OVWYV0rh0WAznJkQYThcxic6d5uAq4iH85vebTwaaSXJstSSQAks0d0vUpBUeQ"
                                   "/RAT9VD2iUsvZyMJZCRz2bhhg88iqu+V1pbg0o//fmeR2gRBxLBM"
                                   "+FsIeibCk5tUyxR7e3uargIWSGrLu5pt"
                                   "+dbEmJsZ0QPHeQ5gdRDnquChUdSxFIvfVkKahtIj6I6nZ9FnIVVGlnSN0sKg9YYxAB1tLVJipXnQ=="}
        response = wse.verify_authenticity(request_json)
        wse.PUBLIC_KEY_SPRING = key
        self.assertEquals(response, None)

    def test_verify_authenticity_not_encoded(self):
        json_message = {"message": "test", "signature": "test"}
        response = wse.verify_authenticity(json_message)
        self.assertTrue(response is None)

    def test_verify_authenticity_wrong_signature(self):
        request_json = {"signature": "S7m8jELzdE+ZjJAXNfIiJNAgUKIQqnOskA9QtNmBPxESfN8f3fdSDYccEiwAuMSlfNMWC"
                                     "+EcnlO3xwVzP71lDk4QMUgjwQz+G2U35/ZbDIpSwAS5xzm0980ICkt"
                                     "/SZyGSBOO9fRixYgjGBuJZiXVWpXzKISz7h1BXzhhKRyJqKTuuTLQHPJHINLRfLIseE/8"
                                     "+xIuo8oZ2lXee59iMnKiGO0KKcdHBG4y8siFFuNh+Mp7NuHypvDtqkvCmL0PmOy7VOHysW"
                                     "/7YgRlWPxc3uLzqzrYe1IPWZiN179jlx+NG1ymva42Idh+NwRvRyiekZXU5wXxDlTSd8B/JwWw"
                                     "==",
                        "message": "HE/Sv6SjwaBeG2lI1vqj2niTnGZ2TC/i9nPwH+OwvjP/vF2DMDLNLNUwY5ak2bz0yyKLanZ4hyjN1"
                                   "/K9tuggzYWTafKK/TGDe5o+l6cDSK5K"
                                   "+SaPwpVKFkp3OVWYV0rh0WAznJkQYThcxic6d5uAq4iH85vebTwaaSXJstSSQAks0d0vUpBUeQ"
                                   "/RAT9VD2iUsvZyMJZCRz2bhhg88iqu+V1pbg0o//fmeR2gRBxLBM"
                                   "+FsIeibCk5tUyxR7e3uargIWSGrLu5pt"
                                   "+dbEmJsZ0QPHeQ5gdRDnquChUdSxFIvfVkKahtIj6I6nZ9FnIVVGlnSN0sKg9YYxAB1tLVJipXnQ=="}
        response = wse.verify_authenticity(request_json)
        true_request = {"name": "Cthulhu", "searchType": "creature"}
        self.assertNotEquals(response, json.dumps(true_request))

    def test_encrypt_data(self):
        message = "hello world"
        response, signature = wse.encrypt_data(message)
        self.assertTrue(response is not None and signature is not None)

    def test_encrypt_data_none(self):
        message = None
        response, signature = wse.encrypt_data(message)
        self.assertTrue(response is not None and signature is not None)

    def test_encrypt_data_wrong_private_key(self):
        key = wse.PRIVATE_KEY
        wse.PRIVATE_KEY = None
        message = "hello world"
        response, signature = wse.encrypt_data(message)
        wse.PRIVATE_KEY = key
        self.assertTrue(response is None and signature is None)

    def test_encrypt_data_wrong_public_key(self):
        key = wse.PUBLIC_KEY_SPRING
        wse.PUBLIC_KEY_SPRING = None
        message = "hello world"
        response, signature = wse.encrypt_data(message)
        wse.PUBLIC_KEY_SPRING = key
        self.assertTrue(response is None and signature is None)

    def test_body_verification(self):
        message = json.dumps({"name": "Cthulhu", "searchType": "creature"})
        search_name, search_type = test_body(message)
        self.assertTrue(search_name == "Cthulhu" and search_type == "creature")

    def test_body_verification_unexpected_key(self):
        message = json.dumps({"names": "Cthulhus", "searchTypes": "creatures"})
        search_name, search_type = test_body(message)
        self.assertTrue(search_name is None and search_type is None)

    def test_body_verification_invalid(self):
        message = None
        search_name, search_type = test_body(message)
        self.assertTrue(search_name is None and search_type is None)

    def test_arrange_creature_data(self):
        for search_name, search_type in creature_param_list:
            with self.subTest():
                message = json.dumps({"name": search_name, "searchType": search_type})
                response_json = arrange_creature_data(message)
                expected_response = [{'name': "Cthulhu",
                                      'description': "Master of R'lyeh",
                                      'image': "h",
                                      'author': "Howard Phillips Lovecraft",
                                      'book': "The Call of Cthulhu",
                                      'year': 1926,
                                      'affiliation': "Great Old Ones"}]
                self.assertEqual(response_json, expected_response)

    def test_arrange_author_data(self):
        for search_name, search_type in author_param_list:
            with self.subTest():
                message = json.dumps({"name": search_name, "searchType": search_type})
                response_json = arrange_author_data(message)
                expected_response = [{'name': "Howard Phillips Lovecraft",
                                      'date': "(1890 - 1937)",
                                      'image': "h",
                                      'book': ["The Call of Cthulhu (1926)"],
                                      'creature': ["Cthulhu"]}]
                self.assertEqual(response_json, expected_response)

    def test_arrange_book_data(self):
        for search_name, search_type in book_param_list:
            with self.subTest():
                message = json.dumps({"name": search_name, "searchType": search_type})
                response_json = arrange_book_data(message)
                expected_response = [{'name': "The Call of Cthulhu",
                                      'year': "(1926)",
                                      'image': "h",
                                      'author': ["Howard Phillips Lovecraft (1890 - 1937)"],
                                      'creature': ["Cthulhu"]}]
                self.assertEqual(response_json, expected_response)

    def test_arrange_affiliation_data(self):
        for search_name, search_type in affiliation_param_list:
            with self.subTest():
                message = json.dumps({"name": search_name, "searchType": search_type})
                response_json = arrange_affiliation_data(message)
                expected_response = [{'name': "Great Old Ones",
                                      'creature': ["Cthulhu"]}]
                self.assertEqual(response_json, expected_response)

    def test_arrange_data_wrong_type(self):
        for arrange_function in functions_list:
            with self.subTest():
                message = json.dumps({"name": "Great Old Ones", "searchType": "test"})
                response_json = arrange_function(message)
                expected_response = []
                self.assertEqual(response_json, expected_response)

    def test_arrange_data_wrong_tag(self):
        for arrange_function in functions_list:
            with self.subTest():
                message = json.dumps({"names": "Great Old Ones", "searchesTypes": "test"})
                response_json = arrange_function(message)
                expected_response = []
                self.assertEqual(response_json, expected_response)

    def test_get_page(self):
        for url in url_list:
            with self.subTest():
                response = self.client.get(url)
                self.assertEqual(response.status_code, 405)

    def test_post_page_without_body(self):
        for url in url_list:
            with self.subTest():
                response = self.client.post(url)
                self.assertEqual(response.status_code, 406)

    def test_creature(self):
        request_json = {"signature": "S7m8jELzdE+ZjJAXNfIiJNAgUKIQqnOskA9QtNmBPxESfN8f3fdSDYccEiwAuMSlfNMWC"
                                     "+EcnlO3xwVzP71lDk4QMUgjwQz+G2U35/ZbDIpSwAS5xzm0980ICkt"
                                     "/SZyGSBOO9fRixYgjGBuJZiXVWpXzKISz7h1BXzhhKRyJqKTuuTLQHPJHINLRfLIseE/8"
                                     "+xIuo8oZ2lXee59iMnKiGO0KKcdHBG4y8siFFuNh+Mp7NuHypvDtqkvCmL0PmOy7VOHysW"
                                     "/7YgRlWPxc3uLzqzrYe1IP5EXrzNWZiN179jlx+NG1ymva42Idh+NwRvRyiekZXU5wXxDlTSd8B/JwWw"
                                     "==",
                        "message": "NjSdsSG3BIUjLn199aNLrfk7hAf5hbrJb3TQF0+ofF/NbuXKiXo0sDcn8oocLr667O2sG"
                                   "+eXO1coKO6qc9unhsnKyamJof/tSts2n9tqJQltam6RsWFZJBukeOr34iik14mlXoziOkBjzEMHLz8"
                                   "+FHcMIJ8tDSHSkeI/KEA3xUBHJrQ94PoO50yoVhbb0dZvHshJTnDU9p7RBVfh6NxO6d2E"
                                   "/a6DgTqFpWqSBxPkEXHaRkTW6bNeGlE24jCGdPJrJ0evKkAhLhPl"
                                   "/xYwTd7gtthmIqCiZ7t8gWq5mTUaPPuspnfUPkga0nf6eint5rYxdlszsN7zATrkn20s9uiNFA=="}
        response = self.client.post('/web_service/creatureSearch', json.dumps(request_json),
                                    content_type="application/json")
        self.assertEqual(response.status_code, 200)

    def test_author(self):
        request_json = {
            "signature": "SW+bryvJ3m2nmQtpS2ztWbpnuv4TzUlQHBUubMBSI8WshXr+330qNN824rzLB+7CPprfpQQx90rHUJi9nd"
                         "Z1Z8SnXRToZ+epj+WDcLPpGuWjKR7S2oEKcdwVKecijUA5biYEO1v4hwc8z9AsLrlWCcsZrMgXo7jn3r/n"
                         "FctS9wY7WXALAixYQqDCUrVS6qwLi2N4eKTFoPzsqGjBQojR7g0+r8Wk9hLRWr7JahpD/HQt/HQe2/00dD"
                         "q57J1xNfwWg8djd/Z6sKB2wiggjrujqGBEMTAq7iNLmLdYXTHnW3ZO2Fb26WWzbqAddZ9i/myP58gGddPb"
                         "e2jbJCOHeKL2dw==",
            "message": "F3BS46YYv3X0qz85MENq5kn2x+kY+Ss3NMsPQxuLfw878HfTaAeBB0dzsVYlTL8CY+flx5t3I0bOUcqbSmhTLCd48aSzJy1"
                       "4wx+SNhQU7wxe0LFUP95D5XkQwcjyNIRoK015MRds7el2uqvqWKHDgNEwcU10rTxa37NipwRrTiN7Iqz09v6nvNJz9S/CXN"
                       "EHRYlJbbjt5okVTOavHVIP+vioX16zXQTIwItaL9uQMcmURYTWX4xxzo4byX3Qod/WvFBL/CWirlRkFo3rT79xBcWLgHW0+"
                       "1yQX+1kbKIaNZ21MtgpZUUviuAzk+krMwWkZb5wompG9tsRSDPJ6kUOdg=="
        }

        response = self.client.post('/web_service/authorSearch', json.dumps(request_json),
                                    content_type="application/json")
        self.assertEqual(response.status_code, 200)

    def test_book(self):
        request_json = {"signature": "nytI4r2F5Luvglibvh0e2/1jaiZjxqcTCAoGdkjWQALuzTJqpJFUKgsuWMoZ9FW/U0K5CPAZ3"
                                     "+RWmupzpUuqLMegtfbUg73OazPjrijzaEFT/RY0DUiJpTgK6pfNelwGzS7V3KkhNiyA3ANT7hXgb"
                                     "/lgIW6is1ENw0KhG+inqrLV0AF5RldQj/+co8nOvEbYA55fmkwICHkZs3GuNwZHipoHGZU"
                                     "/AouyKvITXweDoEW2Jc1yMH31NJBrajDTGCfHaOzLJfu65FBQGe2KBwgZkKGUqEvFlcMa5N"
                                     "+DAw1iT2TVe3r1q0xNxuzMmeIvLSUSh8zxmaa9sUkTJqajVtjOcA==",
                        "message": "tORa/grk7QG7TVKnslCSJE0uy9u7kxehWMwLZXf+yCWNXs1bjjl7jiWrHv0+/oTWpdBj8Tpt"
                                   "/FKuUCSrXwX4xdTxA/oq+xI4gieIRufbM7GuQUhCluoQh/7U"
                                   "/v40oGCxPs6LRJo7EnJsJbSQhTd6B5cMWH1CgLSBDaXs4x7BCDRypWXdqata6FdUOskZnCJS61bAqjl4tG"
                                   "X6k6k9ZZ2VlmvNk79YyJsx8g6O5vsHCHsxiq+NxmRyWm7DtxBeLxHL7SixRZsJCYZyJ3vbfX6mCXyoXYgsr"
                                   "K5ugl9Ako6mhV4xaA3Jeagm/RoOj+GldzN7g1f22CEx0/8ZUtwneDmPCA=="}

        response = self.client.post('/web_service/bookSearch', json.dumps(request_json),
                                    content_type="application/json")
        self.assertEqual(response.status_code, 200)

    def test_affiliation(self):
        request_json = {
            "signature": "S7m8jELzdE+ZjJAXNfIiJNAgUKIQqnOskA9QtNmBPxESfN8f3fdSDYccEiwAuMSlfNMWC"
                         "+EcnlO3xwVzP71lDk4QMUgjwQz+G2U35/ZbDIpSwAS5xzm0980ICkt"
                         "/SZyGSBOO9fRixYgjGBuJZiXVWpXzKISz7h1BXzhhKRyJqKTuuTLQHPJHINLRfLIseE/8"
                         "+xIuo8oZ2lXee59iMnKiGO0KKcdHBG4y8siFFuNh+Mp7NuHypvDtqkvCmL0PmOy7VOHysW"
                         "/7YgRlWPxc3uLzqzrYe1IP5EXrzNWZiN179jlx+NG1ymva42Idh+NwRvRyiekZXU5wXxDlTSd8B/JwWw==",
            "message": "xCNWqJ9RC+8nhLd275"
                       "+6tTqszulnaTHMtUR7hPO3FSq8MeTvzsvII1WXfZSUFN5pQEpDFuTNL9a4LupLoBjaBfzM0FlD9HUImgH1GF"
                       "/bl3bHlCXiUfYeKwLYFo87rxISpDjC2SX+FWl9hOUUggjLRzPirnEkhRG1RCerGeeFIC2BlklPaa8I0GjpON45T3hqf"
                       "/blETeKi4z+N7d4vFOz02ods3OabYLn+FycfyW54BmpnpQcNmjVBMDtHCwooOX4uFU84FUdqOGGBwUxIuQ9Xmfye05Shf"
                       "+31qJy/9KY6nV1Yc1aPjGvkhm8aA5uKX2G7EdgHqdANKhzKb2pZ+uGmg=="
        }

        response = self.client.post('/web_service/affiliationSearch', json.dumps(request_json),
                                    content_type="application/json")
        self.assertEqual(response.status_code, 200)

    def test_broken_signature(self):
        request_json = {
            "signature": "IiJNAgUKIQqnOskA9QtNmBPxESfN8f3fdSDYccEiwAuMSlfNMWC"
                         "+EcnlO3xwVzP71lDk4QMUgjwQz+G2U35/ZbDIpSwAS5xzm0980ICkt"
                         "/SZyGSBOO9fRixYgjGBuJZiXVWpXzKISz7h1BXzhhKRyJqKTuuTLQHPJHINLRfLIseE/8"
                         "+xIuo8oZ2lXee59iMnKiGO0KKcdHBG4y8siFFuNh+Mp7NuHypvDtqkvCmL0PmOy7VOHysW"
                         "/7YgRlWPxc3uLzqzrYe1IP5EXrzNWZiN179jlx+NG1ymva42Idh+NwRvRyiekZXU5wXxDlTSd8B/JwWw==",
            "message": "xCNWqJ9RC+8nhLd275"
                       "+6tTqszulnaTHMtUR7hPO3FSq8MeTvzsvII1WXfZSUFN5pQEpDFuTNL9a4LupLoBjaBfzM0FlD9HUImgH1GF"
                       "/bl3bHlCXiUfYeKwLYFo87rxISpDjC2SX+FWl9hOUUggjLRzPirnEkhRG1RCerGeeFIC2BlklPaa8I0GjpON45T3hqf"
                       "/blETeKi4z+N7d4vFOz02ods3OabYLn+FycfyW54BmpnpQcNmjVBMDtHCwooOX4uFU84FUdqOGGBwUxIuQ9Xmfye05Shf"
                       "+31qJy/9KY6nV1Yc1aPjGvkhm8aA5uKX2G7EdgHqdANKhzKb2pZ+uGmg=="
        }

        response = self.client.post('/web_service/affiliationSearch', json.dumps(request_json),
                                    content_type="application/json")
        self.assertEqual(response.status_code, 503)
