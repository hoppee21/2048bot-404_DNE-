import cv2 as cv
from matplotlib import pyplot as plt

def TemplateMatch (source, temp):
    img = cv.imread(source, 0)
    template = cv.imread(temp, 0)
    w, h = template.shape[::-1]

    method = eval('cv.TM_SQDIFF')
    # Apply template Matching
    res = cv.matchTemplate(img, template, method)
    min_val, max_val, min_loc, max_loc = cv.minMaxLoc(res)
    # If the method is TM_SQDIFF or TM_SQDIFF_NORMED, take minimum
    if method in [cv.TM_SQDIFF, cv.TM_SQDIFF_NORMED]:
        top_left = min_loc
    else:
        top_left = max_loc
    bottom_right = (top_left[0] + w, top_left[1] + h)
    cv.rectangle(img, top_left, bottom_right, 255, 2)
    plt.subplot(121), plt.imshow(res, cmap='gray')
    plt.title('Matching Result'), plt.xticks([]), plt.yticks([])
    plt.subplot(122), plt.imshow(img, cmap='gray')
    plt.title('Detected Point'), plt.xticks([]), plt.yticks([])
    plt.suptitle('cv.TM_SQDIFF')
    plt.show()
    #print(top_left, bottom_right)
    return top_left, bottom_right
