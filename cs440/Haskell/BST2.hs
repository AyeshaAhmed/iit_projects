data BST a = Node a (BST a) (BST a)
     	   | Empty
     deriving (Show,Eq)

add elt Empty = Node elt Empty Empty
add elt orig@(Node k lc rc)
  | elt == k	   = orig
  | elt < k	   = Node k (add elt lc) rc
  | otherwise	   = Node k lc (add elt rc)

addlist xx = aux xx Empty
  where aux [] t     = t
  	aux (x:xs) t = aux xs (add x t)

find desire Empty = False 
find desire (Node k lc rc)
  | desire == k  = True 
  | desire < k 	 = find desire lc
  | otherwise 	 = find desire rc

del victim Empty = Empty 
del victim orig@(Node k lc rc)
  | victim == k =
    case orig of 
      Node _ Empty Empty -> Empty
      Node _ lc Empty -> lc
      Node _ Empty rc -> rc
      Node _ lc rc -> getPred rc
  | victim < k  = Node k (del victim lc) rc
  | otherwise   = Node k lc (del victim rc)

getPred Empty			 = Empty
getPred orig@(Node k lc rc)
	| lc == Empty		 = orig
	| otherwise		 = getPred lc


-- For testing use
-- let myBST = addlist [10,15,12,20,6,3,9]