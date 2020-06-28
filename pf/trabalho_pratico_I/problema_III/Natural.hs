module Natural where

import Prelude(Eq, Ord, Int, Bool(True, False), (-), (+), (==), (<=))

data Nat = Zero | Suc Nat

intToNat :: Int -> Nat
intToNat 0 = Zero
intToNat n = Suc (intToNat (n - 1))

natToInt :: Nat -> Int
natToInt Zero = 0
natToInt (Suc n) = 1 + natToInt n


instance Eq Nat where
    Zero == Zero = True
    Zero == _ = False
    _ == Zero = False
    Suc x == Suc y = x == y

instance Ord Nat where
    Zero <= Zero = True
    _ <= Zero = False
    Zero <= _ = True
    Suc x <= Suc y = x <= y