-- 1)
-- kmap works when we enter-> kmap () [] (\x -> x) or instead of using id you can also just put show.

kmap _ []	k = k []
kmap f (x:xs) 	k = kmap f xs (\res -> k $ (f x):res)

-- 2)
-- kfoldr works when we enter-> kfoldr () 0 [] (\x -> x) or instead of using id you can also just put show.

kfoldr _ z []		k = k z
kfoldr f z (x:xs)	k = kfoldr f z xs (\res -> k $ (f x) res)

-- 3)
-- afoldr works when we enter-> afoldr () 0 [] (\x -> x) or instead of using id you can also just put show.

afoldr f z xx k = aux f z xx k
       where aux _   z []     ka = ka z
       	     aux (*) z (0:xs) ka = k 0
	     aux f   z (x:xs) ka = kfoldr f z xs 
	     	       	      	      	     (\res -> ka $ (f x) res)

-- 4) 
-- So, my afoldr goes through each element of the list until it reaches the end then applies the operation (f) to z and the last element of the list. While initially going through the list if the afoldr finds a zero being multiplied it immediately aborts the process and returns zero before any multiplication is done.