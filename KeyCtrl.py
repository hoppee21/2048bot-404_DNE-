from pynput.keyboard import Key, Controller


def KeyboardCtrl(OP):
    keyboard = Controller()

    if (OP == 'up'):
        keyboard.press(Key.up)
        keyboard.release(Key.up)
    elif (OP == 'down'):
        keyboard.press(Key.down)
        keyboard.release(Key.down)
    elif (OP == 'left'):
        keyboard.press(Key.left)
        keyboard.release(Key.left)
    elif (OP == 'right'):
        keyboard.press(Key.right)
        keyboard.release(Key.right)
