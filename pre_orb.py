import os
import cv2 as cv

def getphoto(path):

    photo_path = path
    file_list = os.listdir(photo_path)
    return file_list

def orb_detector(file_name):

    img = cv.imread('2048_data\%s'%file_name,cv.IMREAD_GRAYSCALE)
    #cv.imshow('image',gray)
    #cv.waitKey(1000)
    orb = cv.ORB_create(128)
    kp,des = orb.detectAndCompute(img, None)
    return des

def pre_orb(path):

    file_list = getphoto(path)
    descriptors_dict = {}
    
    for file_name in file_list:
        descriptors_dict[file_name[:-4]] = orb_detector(file_name)
    return descriptors_dict

def cache_orb_detect(file_name):

    img = cv.imread('imgcache\%s'%file_name,cv.IMREAD_GRAYSCALE)
    orb = cv.ORB_create(128)
    kp, des = orb.detectAndCompute(img, None)
    return des


def matche(descriptors_dict,curent_descriptors):

    matcher = cv.BFMatcher_create(cv.NORM_HAMMING, crossCheck=True)
    #ID = ''
    s_key = 0
    s_sum = 1000
    for key in descriptors_dict.keys():
        #print(item)
        matchePoints = matcher.match(curent_descriptors,descriptors_dict[key])
        sorted(matchePoints, key=lambda x: x.distance)
        sum = 0
        for i in range(len(matchePoints)):
            sum += matchePoints[i].distance
        # ave_dis = sum(matchePoints[:20].distance)
        sum = sum / len(matchePoints)
        #print(key)
        #print(sum)
        if s_sum > sum:
            s_key = key
            s_sum = sum
    return s_key


        

if __name__ == '__main__':
    descriptors_dict =pre_orb('2048_data')
    #img = cv.imread('2.png')
    #print(matche(descriptors_dict,orb_detect(img)))
    file_list = getphoto('imgCache')
    output = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    for file_name in file_list:
        #print(file_name[:-4])
        des = cache_orb_detect(file_name)
        if des is None:
            #print('0')
            output[int(file_name[:-4])] = '0'

        else:
            #print(matche(descriptors_dict,des))
            RESULT = matche(descriptors_dict,des)
            output[int(file_name[:-4])] = RESULT
            #print(RESULT)

    print(output)
    f = open('data.txt', mode= 'w')
    n = 1
    for e in output:
        f.write(e+',')
        if n == 4:
            f.write('\n')
            n= 0
        n=n+1
    f.close()