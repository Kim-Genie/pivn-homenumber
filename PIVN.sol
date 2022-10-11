// SPDX-License-Identifier: MIT
pragma solidity ^0.8.2;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/token/ERC20/extensions/ERC20Burnable.sol";
import "@openzeppelin/contracts/token/ERC20/extensions/ERC20Pausable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
//import "./LockToken.sol";

contract PIVN is ERC20, ERC20Pausable, Ownable, ERC20Burnable{
    string public constant _name = "PIVN";
    string public constant _symbol = "PIVN";
    uint64 public constant INITIAL_SUPPLY = 100000000000;

    // Lock
    mapping (address => uint256) public lockAddress;
    address[] public addressLogArray;

    address _sale      = 0xb9E8AAb1f839d9BC88A674daf4DF045A6E38bECC;
    address _team      = 0xa1A88dFD3d7b50FE386Ce126c9A5e3F785bc43fA;
    address _company   = 0xb3B4c9250946D98B78c07f738EE2Cdc7aB74459a;
    address _liquidity = 0x954379c77b8Dfb6de8705230738B9E1FA6e73Cb0;
    address _rnd       = 0x4E210009aA8224e78D8eEcb7d890f6Fac0F049AB;
    address _maketing  = 0xA00b0280A2B56817b2E4e453C7e4aD978005136a;

    constructor() ERC20(_name, _symbol) {
        _mint(msg.sender, INITIAL_SUPPLY * 10 ** decimals());

        transfer(_sale,      (INITIAL_SUPPLY / 100 * 10 ) * 10 ** decimals());
        transfer(_team,      (INITIAL_SUPPLY / 100 * 10 ) * 10 ** decimals());
        //transfer(_company,   (INITIAL_SUPPLY / 100 * 50 ) * 10 ** decimals());
        //transfer(_liquidity, (INITIAL_SUPPLY / 100 * 10 ) * 10 ** decimals());
        //transfer(_rnd,       (INITIAL_SUPPLY / 100 * 10 ) * 10 ** decimals());
        //transfer(_maketing,  (INITIAL_SUPPLY / 100 * 10 ) * 10 ** decimals());

        fncLockAddress(_team, (block.timestamp + (24 * 60 * 60 * 365 * 4)));
        //fncLockAddress(_sale, (block.timestamp + (24 * 60 * 60 * 365 * 1)));
        //transferOwnership(_nextOwner);
    }

    function decimals() public view virtual override returns (uint8) {
        return 18;
    }

    function _beforeTokenTransfer(address from, address to, uint256 tokenId)
        internal
        override(ERC20, ERC20Pausable){
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function isLockAddress(address _addr) public view returns (bool lock) {
        lock = false;

        if(lockAddress[_addr] > block.timestamp) {
            lock = true;
        }
    }

    function transfer(address to, uint256 amount) public override returns (bool) {
        address owner = _msgSender();

        require(!isLockAddress(owner), "Lock Address");

        _transfer(owner, to, amount);
        return true;
    }

    function transferFrom(
        address from,
        address to,
        uint256 amount
    ) public virtual override returns (bool) {
        address spender = _msgSender();

        require(!isLockAddress(from), "Lock Address");

        _spendAllowance(from, spender, amount);
        _transfer(from, to, amount);
        return true;
    }

    function fncLockAddress(address _lockAddress, uint256 _releaseTime) onlyOwner public {
        lockAddress[_lockAddress] = _releaseTime;
        addressLogArray.push(_lockAddress);
    }

    /*
    function lockToken(address beneficiary, uint256 amount, uint256 releaseTime) onlyOwner public {
        LockToken lockContract = new LockToken(this, beneficiary, msg.sender, releaseTime);

        transfer(address(lockContract), amount);
        lockStatus[beneficiary] = address(lockContract);
        emit Lock(beneficiary, amount);
    }
    */
    function getBlockTime() onlyOwner
        public
        view
        returns (uint256){
        return block.timestamp;
    }

    function getAllLockAddress() public view returns (uint) {
        return addressLogArray.length;
    }
}
