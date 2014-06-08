from django.shortcuts import render

# Create your views here.

from django.db.models import Q
from django.shortcuts import render_to_response
from books.models import Book
from books.forms import ContactForm
from django.core.mail import send_mail
from django.http import HttpResponseRedirect
def search(request):
    query = request.GET.get('q','')
    if query:
        qset = (
                Q(title__icontains=query)|
                Q(authors__first_name__icontains=query)|
                Q(authors__last_name__icontains=query))
        result = Book.objects.filter(qset).distinct()
    else:
        result = []
    return render_to_response('books/search.html',{
                    'results':result,
                    'query':query
                    })
def contact(request):
    if request.method == 'POST':
        form = ContactForm(request.POST)
        if form.is_valid():
            topic = form.clean_data['topic']
            message = form.clean_data['message']
            sender = form.clean_data.get('sender', 'noreply@example.com')
            send_mail(
                'Feedback from your site, topic: %s' % topic,
                message, sender,
                ['administrator@example.com']
            )
            return HttpResponseRedirect('/contact/thanks/')
    else:
        form = ContactForm()
    return render_to_response('contact.html', {'form': form})