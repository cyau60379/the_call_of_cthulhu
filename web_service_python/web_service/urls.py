from django.urls import path
from . import views

urlpatterns = [
    path('creatureSearch', views.creature_search, name='creatureSearch'),
]
