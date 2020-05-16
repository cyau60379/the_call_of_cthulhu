from django.urls import path
from . import views

urlpatterns = [
    path('creatureSearch', views.creature_search, name='creatureSearch'),
    path('authorSearch', views.author_search, name='authorSearch'),
    # if the URL contains creatureSearch after web_service/, execute creature_search
]
