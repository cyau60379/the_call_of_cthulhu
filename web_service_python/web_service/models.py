from django.db import models


class Affiliation(models.Model):
    name = models.CharField(max_length=200)


class Creature(models.Model):
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
    image_link = models.CharField(max_length=200)
    affiliation = models.ForeignKey(Affiliation, on_delete=models.CASCADE)


class Author(models.Model):
    first_name = models.CharField(max_length=200)
    surname = models.CharField(max_length=200)
    birth_date = models.DateField()
    death_date = models.DateField()
    image_link = models.CharField(max_length=200)


class Book(models.Model):
    name = models.CharField(max_length=200)
    year_of_creation = models.DateField()
    image_link = models.CharField(max_length=200)


class CreatureBook(models.Model):
    creature = models.ForeignKey(Creature, on_delete=models.CASCADE)
    book = models.ForeignKey(Book, on_delete=models.CASCADE)


class AuthorBook(models.Model):
    author = models.ForeignKey(Author, on_delete=models.CASCADE)
    book = models.ForeignKey(Book, on_delete=models.CASCADE)


class AuthorCreature(models.Model):
    author = models.ForeignKey(Author, on_delete=models.CASCADE)
    creature = models.ForeignKey(Creature, on_delete=models.CASCADE)
