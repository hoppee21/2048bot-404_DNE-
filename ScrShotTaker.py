import pyautogui

def ScrShot():
    im = pyautogui.screenshot()
    im.save('SrcShot/Src.png')