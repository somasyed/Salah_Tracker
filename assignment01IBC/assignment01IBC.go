package assignment01IBC

import (
	"crypto/sha256"
	"fmt"
  //"encoding/hex"
)


type Block struct {
transaction string
prevPointer *Block
prevhash [32]byte

}

func InsertBlock(transaction string, chainHead *Block) *Block {
  var block *Block=new(Block)
  //print("ds")
  if(transaction=="GenesisBlock" || chainHead==nil){
  //  print("if mai ha")
    block.transaction=transaction

    block.prevPointer=chainHead
  } else{

    block.transaction=transaction
    block.prevPointer=chainHead
    block.prevhash=sha256.Sum256([]byte( chainHead.transaction ))
  }

// fmt.Println(block.prevPointer,block.prevhash,block.transaction)
 fmt.Println("New block inserted")
fmt.Println(" ")
return block

}

func ListBlocks(chainHead *Block) {
  fmt.Println("Displaying Complete list \n")
  var block *Block=chainHead
  for i:= 0;i<i+1;i++ {

    if(block.prevPointer!=nil){
      fmt.Println("Transaction ->",block.transaction)
      fmt.Println("Previous hash ->",block.prevhash)
      fmt.Println(" ")
      block=block.prevPointer
    } else{
      fmt.Println("Transaction ->",block.transaction)
      fmt.Println("Previous hash ->",block.prevhash)
      fmt.Println(" ")
      break
    }

  }
  fmt.Println(" ")
}

func ChangeBlock(oldTrans string, newTrans string, chainHead *Block) {
  var block *Block=chainHead
  for i:= 0;i<i+1;i++ {

    if(block.transaction==oldTrans){
      fmt.Println("Change made")

      block.transaction=newTrans
      break
    }
    if(block.prevPointer==nil){
      break
}
block=block.prevPointer

    }

  chainHead=block
}


func VerifyChain(chainHead *Block) {
  var block *Block=chainHead
  var check=0
  for i:= 0;i<i+1;i++ {

    if(block.prevhash!=sha256.Sum256([]byte( block.prevPointer.transaction ))){

      check=1
      break
    }
    if(block.prevPointer.prevPointer==nil){
      break
}
block=block.prevPointer

    }
    if(check==1){
        fmt.Println("Block chain is Tempered")
    }else{

    fmt.Println("Block chain is NOT TEMPERED")
}
}
