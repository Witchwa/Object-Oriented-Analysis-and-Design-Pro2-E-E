# -*- coding: utf-8 -*-
from django.shortcuts import render
from django.shortcuts import render_to_response
from django.http import HttpResponse
from django.http import Http404
import json as jsonGen
from models import Runner
import threading
import time
from hashlib import md5

# Create your views here.

locationHash = {}
shakingUsers = set()
mu = threading.Lock()


def checkLogin(request):
    if 'user_id' in request.session.keys():
        return True
    return False

class ServerThread(threading.Thread):
    def run(self):
        while True:
            time.sleep(1)
            curTime = int(time.time())
            if curTime % 10 == 0:
                if mu.acquire():
                    locationHash = {}
                    mu.release()
#since django not support multi thread in this way WRONG
# serverThread = ServerThread()
# serverThread.run()
def login(request):
    if request.method != 'POST':
        return render_to_response('account/login.html',{'title':'login'})    
        #raise Http404('Only POSTs are allowed')
    else:
        username = request.POST['userName']
        pwd = request.POST['password']
        pwd = md5(pwd).hexdigest()
        try:
            user = Runner.objects.get(userName=username)
            if pwd != user.password:
                json = {'code':152,
                    'phase':'password not match'}
                return HttpResponse(jsonGen.dumps(json,ensure_ascii = False))
            else:
                json = {'code':150,
                        'phase':'login success'
                        }
                request.session['user_id'] = user.id
                return HttpResponse(jsonGen.dumps(json,ensure_ascii = False))
    
        except Runner.DoesNotExist:
            json = {'code':151,
                    'phase':'user not exists!'}
            return HttpResponse(jsonGen.dumps(json,ensure_ascii = False))
            
        
            
def registe(request):
    if request.method != 'POST':
        #raise Http404('Only POSTs are allowed')
        return render_to_response('account/registe.html',{'title':'registe'})
    else:
        if checkLogin(request):
            json = {'code':102,
                    'phase':'user already loggin, please logout'}
            return HttpResponse(jsonGen.dumps(json, ensure_ascii = False))
        else:
            userName = request.POST['userName']
            password = request.POST['password']
            phoneNum = request.POST['phoneNum']
            weight   = request.POST['weight']
            height   = request.POST['height']
            password = md5(password).hexdigest()
            
            try:
                user = Runner.objects.get(userName = userName)
                json = {'code':101,
                        'phase':'user already exist'
                        }
                return HttpResponse(jsonGen.dumps(json,ensure_ascii = False ))
        
            except Runner.DoesNotExist:
                user = Runner(userName = userName,password = password,phoneNum = phoneNum,weight = weight,height = height)
                user.save()
                json = {'code':100,
                        'phase':'registe success'}
                return HttpResponse(jsonGen.dumps(json,ensure_ascii = False))
                
            
def logout(request):
    if request.method != 'POST':
        return render_to_response('account/logout.html',{'title':'log out'})
        #raise Http404('Only POST are allowed')
    else:
        if checkLogin(request):
            try:
                del request.session['user_id']
            except KeyError:
                pass
            json = {'code':180,
                    'phase':'logout success'}
            return HttpResponse(jsonGen.dumps(json,ensure_ascii = False))
        else:
            json = {'code':180,
                    'phase':' you did not login'}
            return HttpResponse(jsonGen.dumps(json,ensure_ascii = False))
            
         
def resetPassword(request):
    pass
tmp = 0


def get_Key(loc_str):
    alt,lt = loc_str.strip()[1:-1].split(',')
    loc_key = alt.split('.')[0] + lt.split('.')[0]
    return loc_key
    
def getNearShakingRunner(request):
    global tmp,locationHash,shakingUsers
    if checkLogin(request) == False:
        #raise Http404('Please login')
        return render_to_response('account/login.html',{'title':'login'})
    
    if request.method == 'GET':
        return render_to_response('nearShakingRunner.html',{'title':'get near shaking runners'})
    
    if request.method == 'POST':
        user_loc = request.POST['myLocation']
        loc_key = get_Key(user_loc)
        user_id = request.session['user_id']
        user = Runner.objects.get(id = user_id)
        userName = user.userName
        phoneNum = user.phoneNum
        distance = 100
        aRunner = {'userName':userName,'phoneNum':phoneNum,'distance':distance,'userLocation':user_loc}
        print loc_key
        if mu.acquire():
            if user_id not in shakingUsers:
                shakingUsers.add(user_id)
                if loc_key not in locationHash.keys():
                    locationHash[loc_key] = []
                locationHash[loc_key].append(aRunner)
            mu.release()
        time.sleep(4)
            
        if mu.acquire():
            userlist =  locationHash[loc_key]
            try:
                shakingUsers.remove(user_id)
                if len(shakingUsers) == 0:
                    locationHash = {}
            except:
                print 'shaking user error'
            mu.release()
        tmpUsers = []
        for u in userlist:
            if u['userName'] != userName:
                tmpUsers.append(u)
        userlist = tmpUsers
        #print 'post'
        
         
#                      [{
#                      'userName':'testT',
#                     'userLocation':'(127,110)',
#                     'distance':100
#                      },
#                     {
#                      'userName':'我是花无缺',
#                     'userLocation':'(127,110)',
#                     'distance':100
#                      }]
        if len(userlist) > 0:
            json = {
                'code': 200,
                'phase':'near user found',
                'userlist':userlist[0:5]}
        else:
            json = {
                'code': 201,
                'phase':'near user not found'} 
        return HttpResponse(jsonGen.dumps(json,ensure_ascii=False))
        

def getMyDiet(request):
    pass

def pushMyDiet(request):
    if checkLogin(request) == False:
        #raise Http404('Please login')
        return render_to_response('account/login.html',{'title':'login'})
    
    if request.method == 'GET':
        return render_to_response('diet/pushMyDiet.html',{'title':'push my diet'})
        
    else:
        print 'post'
        print request.POST['ff']
        jsonGen.loads(request.POST['ff'])
        
    
    