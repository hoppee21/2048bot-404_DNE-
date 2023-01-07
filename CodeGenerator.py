for operation in ['up', 'down', 'left', 'right']:
    print("elif (OP == '" + operation + "'):")
    print("\tkeyboard.press(Key." + operation + ")")
    print("\tkeyboard.release(Key." + operation + ")")
