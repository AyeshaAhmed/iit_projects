-- Ayesha Ahmed / CS440 Fall2015 / Recursion Activity

-- //chop takes list of heads to chop and chops in order.

chop []     	      = [0]
chop (0:y:ys)	      = (y:ys)
chop all@(x:y:ys)     = ((x-1):(y+(length all)-1):ys)
chop (0:xs)	      = [0]
chop (x:xs)	      = ((x-1):xs)


-- chopAll chops all the heads in given list and returns the number of chops.

chopAll heads = num heads 0
     where num []	 acc = acc
     	   num [0]	 acc = acc
     	   num (0:xs)	 acc = num xs acc
     	   num (x:xs)	 acc = num (chop(x:xs)) (acc+1)




-- ////////////////  NOTES  ///////////////// 
-- ABOUT LAB: Recursion Activity
-- Hercules has to slay the Hydra. The Hyrdra has nine heads that are “level-9” heads. If one of them is cut off, eight level-8 heads grow to replace it. If you chop one of these,seven level-7 heads show up. This continues until you get to a level-1 head. If you chop that one off, nothing else grows to take its place. The question is this: how many head-choppings does Hercules have to perform to kill the Hydra?

-- //Suggested helper function to get started
maxlevel hydra = aux hydra (length hydra)
  where aux _      0 = 0
        aux []     _ = 0
        aux (0:xs) i = aux xs (i-1)
        aux (x:xs) i = i

-- //Initial attempt:
-- chop []	      acc = acc
-- chop (x:y:ys) acc = chop ((x * (maxlevel (x : ys))) : ys) (x + acc)

-- MORE NOTES:
-- //chop function should take [9,0,0,0,0,0,0,0,0] and returns [8,8,0,0,0,0,0,0,0]
-- //chopAll goes through the whole list [9,0,0,0,0,0,0,0,0] until [0] is reached and records the number of chops preformed.