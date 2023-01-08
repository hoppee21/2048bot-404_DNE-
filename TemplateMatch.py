import cv2 as cv

def TemplateMatch (source, temp):
    img = cv.imread(source, 0)
    template = cv.imread(temp, 0)
    w, h = img.shape[::-1]

    method = eval('cv.TM_SQDIFF')
    # Apply template Matching
    res = cv.matchTemplate(img, template, method)
    min_val, max_val, min_loc, max_loc = cv.minMaxLoc(res)
    top_left = min_loc

    img_rev = cv.rotate(img, cv.ROTATE_180)
    temp_rev = cv.rotate(template, cv.ROTATE_180)
    method = eval('cv.TM_SQDIFF')
    # Apply template Matching
    res = cv.matchTemplate(img_rev, temp_rev, method)
    min_val, max_val, min_loc, max_loc = cv.minMaxLoc(res)
    #print(w, h)
    #print(min_loc)
    bottom_right = (w - min_loc[0], h-min_loc[1])
    #print(top_left, bottom_right)
    return top_left, bottom_right
