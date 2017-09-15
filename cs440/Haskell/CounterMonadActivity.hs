-- Answer to question 1
-- Suppose you feed minc (Just 10). The bind operator will pull/unbox the 10 out and feed it to the to the \xx -> return (xx + 1). This will result in return 10 + 1 which is return 11. And return x for the Maybe monad is defined as Just x, So we get Just 11 back.


-- Answer to question 2
-- In this case the bind behaves like a map. So, return x is defined as a list [x]. Since the input can contain a lot of things, it gets mapped over all of them, and the results get concatenated. So minc [1,3,4] would become concatMap (\x -> [x]) [1,3,4]. Which is then [2,4,5].


-- Answer to question 3
data Counter a = Counter a Int
	deriving (Show, Eq)

instance Monad Counter where
	return x = Counter x 0
	(>>=) (Counter a i) f = 
		let Counter b j = f a in
		    Counter b (j + i + 1)

inc x = x >>= (\a -> return $ a + 1)

add x y = do
	a <- x
	b <- y
	return $ a +b





