import KeyCtrl
import ScrShotTaker
import TemplateMatch
import imageSplit

if __name__ == "__main__":
    #KeyCtrl.KeyboardCtrl("down")
    ScrShotTaker.ScrShot()
    TestPage = 'ScrShot/Scr.png'
    template = 'template2048.png'
    Top_L, Bottom_R = TemplateMatch.TemplateMatch(TestPage, template)
    print (Top_L, Bottom_R)
    imageSplit.imageSplit16(TestPage, Top_L, Bottom_R)