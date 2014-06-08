'''
Created on Jun 5, 2014

@author: apple
'''

from django.http import HttpResponse
import datetime
from django.template import Context
from django.template.loader import get_template
from django.shortcuts import render_to_response

def current_time(request):
    now = datetime.datetime.now()
    
    t = get_template('current_datetime.html')
    html = t.render(Context({'current_time':now}))
    return render_to_response("current_datetime.html",{"current_time":now})
    #return HttpResponse(html)
def hours_ahead(request,offset):
    offset = int(offset)
    dt = datetime.datetime.now() + datetime.timedelta(hours = offset)
    return render_to_response("future_datetime.html",{"offset":offset,"future_time":dt})

