-- Question one
negList []	= []
negList xx 	= map (\x -> -1 * x) xx

-- Question 2
sumSqr []	= 0
sumSqr xx	= foldr (+) 0.0 (map (**2) xx)

-- Question 3 - If we use only map to rewrite it then it woud only apply square to each element of th list individually but would not add all the elements together so, no we cannot buil the function with only map.

-- Question 4
myzipWith (f) [] []  	     = []
myzipWith (f) [] _           = []
myzipWith (f) _ [] 	     = []
myzipWith (f) (x:xs) (y:ys)  = (f x y) : (myzipWith f xs ys)

-- Question 5 -  too HARD!