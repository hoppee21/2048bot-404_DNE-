import os
import cv2 as cv
import numpy as np

def getphoto():

    photo_path = '2048_data'
    file_list = os.listdir(photo_path)
    return file_list

def orb_detector(file_name):

    img = cv.imread('2048_data/%s'%file_name,cv.IMREAD_GRAYSCALE)
    #cv.imshow('image',gray)
    #cv.waitKey(1000)
    orb = cv.ORB_create(128)
    kp, des = orb.detectAndCompute(img, None)
    return des

def pre_orb():

    file_list = getphoto()
    descriptors_dict = {}
    
    for file_name in file_list:
        descriptors_dict[file_name[:-4]] = orb_detector(file_name)
    return descriptors_dict

def orb_detect(img):
    orb = cv.ORB_create(128)
    kp, des = orb.detectAndCompute(img, None)
    return des


def matche(descriptors_dict,curent_descriptors):

    matcher = cv.BFMatcher_create(cv.NORM_HAMMING, crossCheck=True)
    #ID = ''
    for key in descriptors_dict.keys():
        #print(item)
        matchePoints = matcher.match(curent_descriptors, descriptors_dict[key])
        sorted(matchePoints, key=lambda x: x.distance)
        sum = 0
        for i in range(len(matchePoints)):
            sum += matchePoints[i].distance
        # ave_dis = sum(matchePoints[:20].distance)
        sum = sum / len(matchePoints)
        print(key)
        print(sum)
        

if __name__ == '__main__':
    descriptors_dict =pre_orb()
    img = cv.imread('4.test.png')
    matche(descriptors_dict, orb_detect(img))

        