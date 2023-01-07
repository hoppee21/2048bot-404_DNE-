import KeyCtrl
import cv2 as cv
import TemplateMatch
import imageSplit

if __name__ == "__main__":
    #KeyCtrl.KeyboardCtrl("down")
    TestPage = 'TestPage2.png'
    Top_L, Bottom_R = TemplateMatch.TemplateMatch(TestPage, 'template2048.png')
    print (Top_L, Bottom_R)
    #imageSplit.imageSplit16(TestPage,Top_L, Bottom_R)
