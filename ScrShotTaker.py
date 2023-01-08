import pyautogui

def ScrShot():
    im = pyautogui.screenshot()
    im.save('ScrShot/Scr.png')