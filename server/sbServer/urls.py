from django.conf.urls import patterns, include, url
from view import *
from books.views import search
from books.views import contact
from django.contrib import admin
from Runner.views import *

admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'sbServer.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^time/$',current_time),
    url(r'^time/plus/(\d{1,2})/$',hours_ahead),
    url(r'^books/',search),
    
    url(r'^contact/$',contact),
    url(r'^login$',login),
    url(r'^registe$',registe),
    url(r'^logout',logout),
    
    url(r'^diet/push/?$',pushMyDiet),
    url(r'^diet/get/?$',getMyDiet),
    
    url(r'^runner/getNearShakingRunner',getNearShakingRunner),
    
)
