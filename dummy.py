
import shutil

oVDS = r'usableData/bk851d39.dat'
oVGS = r'usableData/y6u4w0r7.dat'
oDRP = r'usableData/drp.dat'

tVDS = r'bk851d39.dat'
tVGS = r'y6u4w0r7.dat'
tDRP = r'drp.dat'

shutil.copyfile(oVDS, tVDS)
shutil.copyfile(oVGS, tVGS)
shutil.copyfile(oDRP, tDRP)

f = open("h7f5k68k.dat", "w")
f.write("false")
f.close()

