from queue import Queue

class WaterJugs(object):
    def __init__(self, capacities=(10, 7, 3), initial=('', (10, 0, 0)), target=5, num=3):
        self.cap = capacities
        self.init = initial
        self.t = target
        self.size = num

    def test(self, node):
        return self.t in node[1]

    def successors(self, node):
        succ = []
        n=node[1]
        for i in range(self.size):
            for j in range(self.size):
                if i != j and n[i]>0:
                    delta=min(n[i], self.cap[j]-n[j])
                    newList= list(n[:])
                    newList[i]-=delta
                    newList[j]+=delta
                    succ.append(('', tuple(newList)))
        return succ

    def bfs(self):
        visited = set()
        q = Queue()
        q.put([self.init])
        while not q.empty():
            curr_path = q.get()
            nu_node = curr_path[-1]
            if self.test(nu_node):
                for i in range(len(curr_path)):
                    for p in curr_path[i]:
                        if p != '':
                            print(p)
                return
            elif not nu_node in visited:
                visited.add(nu_node)
                children=self.successors(nu_node)
                for c in children:
                    if sum(c[1])>=self.t and not c in visited:
                        nu_path=curr_path + [c]
                        q.put(nu_path)
        print("No solution path found")

def main():
    global wj
    jugcaps = tuple(sorted([int(x) for x in input("Enter jug capacities: ").split()],reverse=True))
    initnum = int(input("Enter initial water amount: "))
    target=int(input("Enter target: "))
    numjugs=len(jugcaps)
    initlist=[0]*numjugs
    for j in range(numjugs):
        if initnum == jugcaps[j]:
            initlist[j]=initnum
    initial=('', tuple(initlist))
    if target>jugcaps[0]:
        print("Target is too big. Target is impossible.")
    elif target>=0 and all(j>0 for j in jugcaps):
        print("#of_Jugs:",numjugs,", capacities:",jugcaps,", initial:",initial[1],", target:",target)
        wj = WaterJugs(capacities=jugcaps, initial=initial, target=target, num=numjugs)
        wj.bfs()
    else:
        print("Invalid entry. Try again.")

if __name__=="__main__": main()
