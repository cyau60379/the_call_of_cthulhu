from django.urls import path
from . import views

urlpatterns = [
    path('creatureSearch', views.creature_search, name='creatureSearch'),
    # if the URL contains creatureSearch after web_service/, execute creature_search
]
