module CreditCard where

import Prelude(Char, Int, read, show, (+), Bool(True, False), (==), (<))
import List(foldl, foldr, length, splitAt)
import Bool(cond)

getDigit :: [Char] -> Int
getDigit xs = read xs::Int

digitToString :: Int -> [Char]
digitToString x = show x

sum :: [Char] -> [Char]
sum xs = cond (num < 10) (numStr') (numStr) where
  num = foldl f 0 xs where
    f acc x = acc + getDigit ([x])
  numStr = digitToString num
  numStr' = '0' : numStr

--addSum
addSum :: [Char] -> [Char]
addSum [] = []
addSum xs = foldr f (sum xs) xs where
  f x acc = x:acc

valid :: [Char] -> Bool
valid xs = cond ((length xs) == 10) (p) (False) where
  p = getDigit (sum digit) == getDigit (checker)
  (digit, checker) = splitAt 8 xs
