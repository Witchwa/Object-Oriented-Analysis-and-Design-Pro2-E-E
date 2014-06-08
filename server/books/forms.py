'''
Created on Jun 5, 2014

@author: apple
'''

import django.forms as forms

TOPIC_CHOICES = (
                 ('general', 'General enquiry'),
                ('bug', 'Bug report'),
                ('suggestion', 'Suggestion'),
                )

class ContactForm(forms.Form):
    topic = forms.ChoiceField(TOPIC_CHOICES)
    message = forms.CharField()
    sender = forms.EmailField(required=True)
    
    def clean_message(self):
        message = self.clean_data.get('message', '')
        num_words = len(message.split())
        if num_words < 4:
            raise forms.ValidationError("Not enough words!")
        return message