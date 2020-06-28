module Maybe where

import Prelude (Bool(True, False), Show)

import Bool
import Functions

-- Aula 4

data Maybe a = Nothing | Just a deriving Show

maybe :: b -> (a -> b) -> Maybe a -> b
maybe y _ Nothing = y
maybe _ f (Just x) = f x

isNothing :: Maybe a -> Bool
isNothing Nothing = True
isNothing _ = False

isJust :: Maybe a -> Bool
isJust = not . isNothing

fromMaybe :: a -> Maybe a -> a
fromMaybe y Nothing = y
fromMaybe _ (Just x) = x

catMaybes :: [Maybe a] -> [a]
catMaybes [] = []
catMaybes (Nothing:xs) = catMaybes xs
catMaybes ((Just x):xs) = x : catMaybes xs

mapMaybe :: (a -> Maybe b) -> [a] -> [b]
mapMaybe f = catMaybes . map f
