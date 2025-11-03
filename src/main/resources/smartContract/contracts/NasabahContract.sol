// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract NasabahContract {

    enum Gender { M, F }
    enum BloodType { A, B, AB, O }

    struct Nasabah {
        bytes32 id;
        uint64 nik;
        string fullName;
        string birthPlace;
        string birthDate;
        Gender gender;
        string religion;
        string maritalStatus;
        BloodType bloodType;
        string fullAddress;
        address owner;
        bool exists;
    }

    struct NasabahInput {
        uint64 nik;
        string fullName;
        string birthPlace;
        string birthDate;
        Gender gender;
        string religion;
        string maritalStatus;
        BloodType bloodType;
        string fullAddress;
    }

    mapping(bytes32 => Nasabah) private nasabahs;
    mapping(address => Nasabah) private nasabahAddress;  // Data akun tiap alamat
    bytes32[] private nasabahIds;

    event NasabahCreated(bytes32 indexed id, address indexed owner, uint64 nik, string fullName, uint256 createTimeStamp);
    event NasabahUpdated(bytes32 indexed id, address indexed owner, uint256 updateTimeStamp);

    function createNasabah(bytes32 id, NasabahInput calldata data) external returns (bytes32) {
        require(!nasabahs[id].exists, "Nasabah already exists");

        nasabahs[id] = Nasabah({
            id: id,
            nik: data.nik,
            fullName: data.fullName,
            birthPlace: data.birthPlace,
            birthDate: data.birthDate,
            gender: data.gender,
            religion: data.religion,
            maritalStatus: data.maritalStatus,
            bloodType: data.bloodType,
            fullAddress: data.fullAddress,
            owner: msg.sender,
            exists: true
        });

        nasabahIds.push(id);
        emit NasabahCreated(id, msg.sender, data.nik, data.fullName, block.timestamp);
        return id;
    }

    function getNasabah(bytes32 id)
    external
    view
    returns (Nasabah memory)
    {
        require(nasabahs[id].exists, "Nasabah not found");
        return nasabahs[id];
    }

    function getAllNasabahIds() external view returns (bytes32[] memory) {
        return nasabahIds;
    }

    function updateNasabah(bytes32 id, NasabahInput calldata data) external {
        require(nasabahs[id].exists, "Nasabah not found");
        require(nasabahs[id].owner == msg.sender, "Not the owner");

        Nasabah storage n = nasabahs[id];
        n.nik = data.nik;
        n.fullName = data.fullName;
        n.birthPlace = data.birthPlace;
        n.birthDate = data.birthDate;
        n.gender = data.gender;
        n.religion = data.religion;
        n.maritalStatus = data.maritalStatus;
        n.bloodType = data.bloodType;
        n.fullAddress = data.fullAddress;

        emit NasabahUpdated(id, msg.sender, block.timestamp);
    }
}
