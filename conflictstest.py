fname = input('Enter file:')
fh = open(fname)

senderDict = dict()
senderList = list()

for line in fh:
    line.rstrip()
    if line.startswith('From:'):
        continue
    if line.startswith('From'):
        words = line.split()
        senderList.append(words[1])

for key in senderList:
     senderDict[key] = senderDict.get(key,0)+1

bigvalue = 0
bigKey = None


for key,value in senderDict.items():
    if  bigvalue < value :
        bigvalue = value
        bigKey = key

print(bigKey,bigvalue)


