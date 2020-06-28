module Main where

import Prelude(IO, Char, Int, Bool,(+), readFile, writeFile,pure, (>>=), putStrLn, Show)
import System.Environment(getArgs)
import Bool
import List
import Functions
import Maybe
import qualified BSTree as BST
import qualified Heap as H

data HuffManTree a = Leaf a | Branch (HuffManTree a) (HuffManTree a) deriving Show

count :: [[Char]] -> BST.BSTree [Char] Int
count = foldr f BST.empty where
    f str tree = cond (BST.contains tree str) tree1 tree2 where
        tree1 = BST.update tree str (+1)
        tree2 = BST.insert tree (str, 1)

createHeap :: BST.BSTree [Char] Int -> H.Heap Int (HuffManTree [Char])
createHeap = f . BST.inOrder where
    f = foldl f' H.empty where
        f' h (k, v) = H.insert h (v, (Leaf k))

--No clean eu estou removendo esse caracteres, mas aparentemente funciona caso nao remova
--so perde um pouco a coerência pois "ola." != "ola"
--Depois de fazer um map em words eu intercalo por ["\\n"], para ganhar mais precisao 
--Caso contrário teria chaves como jose\nantonia sendo tratado com apenas uma palavra
--E com0 nao da para armazenar o '\n' no arquivo eu coloco como sendo a string "\\n"
--E assim substituo o caractere especial por um caractere normal 
process :: [Char] -> [Char]
process x = makeOutput (countWords (clean x)) where
    clean = remove ',' . remove '(' . remove ')' . remove '?' . remove '!' . remove '.'
    words' = concat. intercalate ["\\n"] . map words . lines
    countWords = count . words'
    makeOutput = makeString . encoding . createHuffManTree . createHeap
    makeString bst = (++) (concat (map (f) (words' x))) (foldr f' [] (BST.inOrder bst)) where
        f x = fromMaybe "" (BST.lookup bst x)
        f' (k, v) acc = concat [acc, "\n", k, " ", v]

createHuffManTree :: H.Heap Int (HuffManTree [Char]) -> HuffManTree [Char]
createHuffManTree (H.Heap (_, huff) []) = huff
createHuffManTree h = createHuffManTree (h') where
    h' = H.insert (H.pop h'') ((k' + k''), (Branch v' v''))
    Just (k', v') = (H.lookup h)
    h'' = H.pop h
    Just (k'', v'') = (H.lookup h'')

encoding :: HuffManTree [Char] -> BST.BSTree [Char] [Char]
encoding huff = f huff "" BST.empty where
    f (Leaf x) cod bst =  BST.insert bst (x, reverse cod)
    f (Branch h1 h2) cod bst = (f h2 ('1':cod) (f h1 ('0':cod) bst))

isValid :: BST.BSTree [Char] [Char]-> [Char] -> Bool
isValid = BST.contains

--Após particionar a string binaria em particoes que sao chaves de uma BST que relacionam
--a palavra a sua decodificao, no entando no caso desse caractere ser o "\\n" eu troco por
-- "\n" pois eu quero que ele fique novamente especial
--Apos intercalar as palavras por espacos, as palavras ficam como "jose \n antonia"
-- e nao e desejavel que haja esse espacos entre os '\n' assim uso a funcao removeSpaces
-- que remove espacoes que estao intercados por '\n'
process2 :: [Char] -> [Char]
process2 = makeOutput . decoded . decodedTree where
    decodedTree = makeTree . lines
    decoded (str, bst) = (bst, (encodingSplit str bst)) 
    makeOutput (bst, enc) = removeSpaces (concat (intercalate " " (map f enc)))where
        f x = f' (fromMaybe "erro!!!" (BST.lookup bst x)) where
            f' "\\n" = "\n"
            f' x' = x'
        removeSpaces [] = []
        removeSpaces l@(_:[]) = l
        removeSpaces l@(x:y:[]) = l
        removeSpaces l@(x:y:z:[]) = l
        removeSpaces (' ' : '\n':' ':xs) = '\n' : removeSpaces xs
        removeSpaces (x:xs) = x : removeSpaces xs

makeTree :: [[Char]] -> ([Char], BST.BSTree [Char] [Char])
makeTree (x:xs) = (x, (foldr f BST.empty xs)) where
    f x acc =  BST.insert acc (k, v) where
        [v, k] = words x
        
encodingSplit :: [Char] -> BST.BSTree [Char] [Char] -> [[Char]]
encodingSplit [] _ = []
encodingSplit xs bst =  x : (encodingSplit qs bst) where
    Just x = find (BST.contains bst) (inits xs)
    lenX = length x
    (_, qs) = splitAt lenX xs

help :: [Char]
help = concat [
    "Ops!!! Paramentros invalidos, veja as opcoes de executar esse program\n\n",
    "./HuffMan.exe \"input.txt\" - codifica input.txt em output.txt\n",
    "./HuffMan.exe \"input.txt\" \"output.txt\" - codifica input.txt em output.txt\n",
    "./HuffMan.exe -e \"input.txt\" \"output.txt\" - codifica input.txt em output.txt\n",
    "./HuffMan.exe -d \"input.txt\" - decodifica input.txt"
    ]

main :: IO()
main = getArgs >>= treatArgs where
    treatArgs [input] = f input "output.txt"
    treatArgs ["-d", input] = f' input
    treatArgs [input, output] = f input output
    treatArgs ["-e", input, output] = f input output
    treatArgs _ = putStrLn help
    f input output = readFile input >>= pure . process >>= writeFile output
    f' input = readFile input >>= pure . process2 >>= putStrLn