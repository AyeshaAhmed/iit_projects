import Data.HashMap.Strict as H

-- Initial types

type ForthState = (IStack, CStack, Dictionary)

type IStack = [Integer]
initialIStack = []

type CStack = [[String]]
initialCStack = []

-- Type for the symbol dictionary

type Dictionary = H.HashMap String [Entry]

data Entry =
     Prim ([Integer] -> [Integer])
   | Def [String]
   | Num Integer
   | Unknown String

instance Show Entry where
  show (Prim f)    = "Prim"
  show (Def s)     = show s
  show (Num i)     = show i
  show (Unknown s) = "Unknown: " ++ s

-- Dictionary helpers

wrap2 f (x:y:xs) = (f x y):xs
wrap2 f _ = error "Value stack underflow!"

dlookup :: String -> Dictionary -> Entry
dlookup word dict =
  case H.lookup word dict of
    Nothing -> case reads word of
                 [(i,"")] -> Num i
                 _        -> Unknown word
    Just x  -> head x

dinsert :: String -> Entry -> Dictionary -> Dictionary
dinsert key val dict =
   case H.lookup key dict of
      Nothing -> H.insert key [val] dict
      Just x  -> H.insert key (val:x) dict

-- Initial Dictionary

dictionary1 = dinsert "+" (Prim $ wrap2 (+)) H.empty
dictionary2 = dinsert "-" (Prim $ wrap2 (-)) dictionary1
dictionary = dinsert "*" (Prim $ wrap2 (*)) dictionary2

-- The Evaluator

temp=[]

eval :: [String] -> ForthState -> IO ForthState
eval []    (istack, [],     dict) = return (istack, [], dict)
eval words (istack, cstack, dict) =
  case dlookup (head words) dict of
    Num i        -> eval xs (i:istack, cstack, dict)
    Prim f       -> eval xs (f istack, cstack, dict)
    Unknown "."  -> do { putStrLn $ show (head istack);
                             eval xs (tail istack, cstack, dict) }
    Unknown ".S" -> do {printer istack; putStrLn $ "";
			eval xs (istack, cstack, dict)}
    Unknown "dup"-> do {eval xs (head istack:istack, cstack, dict)}
    Unknown "drop"-> do {eval xs (tail istack, cstack, dict)}
    Unknown "swap"-> do { temp <- swapper istack; 
			eval xs (temp, cstack, dict)}
    Unknown "rot" -> do { temp <- rotter istack;
			 eval xs (temp, cstack, dict)}
    --Unknown ":"	  -> do { temp <- restInput(tail istack);
			  --eval xs (istack, cstack, dinsert (putStr $ ""++xs, head istack, dict))}

  where xs = tail words

printer [] = do{putStr $ ""}
printer (x:xs) = do{printer xs; putStr $ " "++ show x}

swapper (x:y:ys) = do{return (y:(x:(ys)))}

rotter (x1:x2:x3:xs) = do{return (x3:x1:x2:xs)}

--restInput (x:xx) = (tail (snd (break == ";")) xx);

repl :: ForthState -> IO ForthState
repl state =
  do putStr "> " ;
     input <- getLine
     nustate <- eval (words input) state
     repl nustate

main = do
  putStrLn "Welcome to your forth interpreter!"
  repl (initialIStack, initialCStack, dictionary)
