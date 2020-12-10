
def match5(val):
	return val*7/5

list = [10, 100, 220, 330, 1000, 2000, 5001, 10000]

for i in list:
	print("{}:{}", i, match5(i))