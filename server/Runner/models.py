from django.db import models

# Create your models here.

class Runner(models.Model):
    userName = models.CharField(max_length = 30)
    password = models.CharField(max_length = 50)
    phoneNum = models.CharField(max_length = 12)
    height = models.FloatField()
    weight = models.FloatField()
    headshot = models.ImageField(upload_to = "./tmp")
    
    def __str__(self):
        return self.userName
    class Admin:
        pass


class Diet(models.Model):
    date = models.DateField()
    user = models.ForeignKey(Runner)
    
    
class Meal(models.Model):
    type = models.CharField(max_length = 20)
    foods = models.CharField(max_length = 100)
    numbers = models.CharField(max_length = 100)
    diet = models.ForeignKey(Diet)

    
    
