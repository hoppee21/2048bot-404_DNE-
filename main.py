import os
import webbrowser
import time
import KeyCtrl

import pre_orb

import ScrShotTaker
import TemplateMatch
import imageSplit

if __name__ == "__main__":
    #KeyCtrl.KeyboardCtrl("down")

    webbrowser.open('https://2048.org/', new = 0, autoraise= True)
    time.sleep(3)
    ScrShotTaker.ScrShot()
    TestPage = 'ScrShot/Scr.png'
    template = 'template2048.png'
    Top_L, Bottom_R = TemplateMatch.TemplateMatch(TestPage, template)
    print (Top_L, Bottom_R)

    imageSplit.imageSplit16(TestPage, Top_L, Bottom_R)
    pre_orb.SecondMain()
    os.system("java -jar manager.jar")

    f = open('dir.txt', mode = 'r')
    dir_ = f.readline()
    KeyCtrl.KeyboardCtrl(dir_)

    while True:
        for i in range (10):
            time.sleep(0.3)
            ScrShotTaker.ScrShot()
            imageSplit.imageSplit16(TestPage, Top_L, Bottom_R)
            pre_orb.SecondMain()
            os.system("java -jar manager.jar")

            f = open('dir.txt', mode='r')
            dir_ = f.readline()
            KeyCtrl.KeyboardCtrl(dir_)
