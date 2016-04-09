
import pygame
import time

from firebase import firebase
firebase = firebase.FirebaseApplication('https://wingbuddy.firebaseio.com', None)

var = 1

while 1 == 1 :
    trueFlag = firebase.get('/music', None)
    time.sleep(1)
    
    print trueFlag

    if trueFlag == 'True' :
        pygame.mixer.init()
        pygame.mixer.music.load("test.wav")
        pygame.mixer.music.play()
        while pygame.mixer.music.get_busy() == True:
            continue
