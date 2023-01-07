import cv2

def imageSplit16 (Source, Top_L, Bottom_R):
    img = cv2.imread(Source)
    step_H = int ((Bottom_R[0] - Top_L[0])/4)
    step_V = int ((Bottom_R[1] - Top_L[1])/4)
    PosFac = 0
    for V in range (Top_L[1], Bottom_R[1], step_H):
        for H in range ((Top_L[0], Bottom_R[0], step_V)):
            cv2.imwrite(f"imgCache/img{PosFac}.png", img[H:H+step_H, V:V+step_V, :])
            PosFac = PosFac + 1