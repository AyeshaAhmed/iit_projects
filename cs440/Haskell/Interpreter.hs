-- ------------------------------ #1) This section declares data types and the 
-- Type Declarations                functions that belong to those different
-- ------------------------------   data types.

data Stmt = ExpStmt Exp
     	  | SetStmt String Exp
     deriving(Show,Read)

data Exp = IntExp Int
     	 | VarExp String
	 | OpExp String Exp Exp
	 | LamExp String Exp
	 | AppExp Exp Exp
	 | LetExp String Exp Exp -- #4) This is declaration of LetExp
     deriving(Show,Read)

data Val = IntVal Int
     	 | CloVal String Exp Env
     deriving(Show,Read)

type Env = [ (String, Val) ]

data State = State String Env

-- ------------------------------ #1) This section defines operator functions
-- Operators                        in intOpList and applies them to 2 values 
-- ------------------------------   using lift to pass the operator and values
--                                  to IntVal which is of the Val data type.

lift op v1 v2 = 
  let IntVal i1 = v1
      IntVal i2 = v2
  in IntVal (op i1 i2)

intOpList = [ ("+", (+)), ("-", (-)) ] -- #3) This is how you add subtraction

-- ------------------------------ #1) This section evaluates the functions 
-- Eval                             (of data type Exp) and the expressions 
-- ------------------------------   given to them.

eval (IntExp i) env = IntVal i
eval (VarExp s) env = 
  case lookup s env of
    Just v -> v
    Nothing -> IntVal 0
eval (LetExp s i body) env = 
     eval body ((s,eval i env) : env) -- #4) This is where LetExp is evaluated.
eval (LamExp s body) env = CloVal s body env
eval (OpExp o e1 e2) env = 
  let v1 = eval e1 env
      v2 = eval e2 env
      op = lookup o intOpList
  in case op of
    Just op' -> lift op' v1 v2
    Nothing -> error "Operator not found."
eval (AppExp e1 e2) env = 
  let v1 = eval e1 env
      v2 = eval e2 env
  in case v1 of
    CloVal s b env' -> eval b ((s,v2) : env')
    _	       	    -> error "Not a function."

-- -------------------------------- #1) This section executes the functions
-- Exec                               (of data type Stmt) by applying the 
-- --------------------------------   operation and printing a statement.

exec (SetStmt s exp) env = 
    State (s ++ " set.") ((s,eval exp env):env)
exec (ExpStmt exp) env = 
    State (show result) env
  where result = eval exp env

-- -------------------------------- #1) This section builds a primitive repl 
-- REPL                               which interprets the given cammands using
-- --------------------------------   the functions defined in this program.
--				      Then the main function initiates the repl.

repl env = do
  putStr "> "
  str <- getLine
  let State result nuenv = exec (read str) env
  putStrLn result
  putStrLn ""
  repl nuenv

main = repl [ ("x",IntVal 1) ]


-- #1) I commented each section with an explanaition of what it does.

-- #2) The example is given as-> ExpStmt (AppExp (LamExp "x" (OpExp "+" (VarExp "x") (IntExp 40))) (IntExp 2))
-- In this example AppExp is evaluated first so that the second parameter (IntExp 2) gets interpreted to the integer value 2. The first parameter is also interpreted as a CloVal case because of the function LamExp which passes the String "x" and body (OpExp "+" (VarExp "x") (IntExp 40)) into the CloVal function which is of the data type Val. Then the CloVal function sets the integer value of String "x" to the value of the second parameter of AppExp which is 2. So, now the value of String "x" = 2. CloVal also passes its second parameter, body, (OpExp "+" (VarExp "x") (IntExp 40)) back into eval which interprets OpExp with 3 parameters. OpExp interprets the first parameter as a plus sign (+) and it gets the integer value of the String "x" which was set to 2 in AppExp. Also, OpExp interpreted the integer value of 40 to be the integer 40 and applies + to 40 and the value of "x" which is 2 so that 40+2 = 42. And that is how we got 42.

-- #3) I have added subtraction interpretation by including it in intOpList and commented in the code on Line 34 where I have done this. And I have verified that it works using the repl.

-- #4) LetExp works when we type something like this ->
-- ExpStmt(LetExp "t" (IntExp 3) (OpExp "+" (VarExp "t") (IntExp 40)))
-- 
-- It even works with subtraction like this:
-- ExpStmt(LetExp "t" (IntExp 3) (OpExp "-" (VarExp "t") (IntExp 40)))

-- #5) Our LetExp function takes 3 arguments so if we look at the declaration of LetExp inside the Exp data type we can see how to make a function with more than one argument.