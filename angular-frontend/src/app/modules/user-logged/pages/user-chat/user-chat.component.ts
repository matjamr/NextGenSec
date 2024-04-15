import {Component, OnInit} from '@angular/core';
import {Observable, of} from "rxjs";
import {Chat} from "../../../../core/models/Chat";

@Component({
  selector: 'app-user-chat',
  templateUrl: './user-chat.component.html',
  styleUrls: ['./user-chat.component.scss']
})
export class UserChatComponent implements OnInit {
  presentChats$: Observable<Chat[]>;
  historicalChats$: Observable<Chat[]>;
  selectedCategory: 'present' | 'historical' = 'present';

  constructor() {
    this.presentChats$ = of(presentChats)
    this.historicalChats$ = of(historicalChats)
  }

  ngOnInit(): void {
  }

  selectCategory(category: 'present' | 'historical') {
    this.selectedCategory = category;
  }

  enterChat(chat: Chat) {
    console.log('Entering chat: ', chat);
  }
}

const historicalChats: Chat[] = [
  {
    "id": "chat3",
    "title": "Year End Party",
    "participants": [
      {
        "id": "user4",
        "name": "Diana Reyes",
        "avatarUrl": "https://example.com/avatar/user4.png"
      },
      {
        "id": "user2",
        "name": "Bob Johnson",
        "avatarUrl": "https://example.com/avatar/user2.png"
      }
    ],
    "lastMessage": {
      "id": "msg3",
      "senderId": "user4",
      "content": "That was a great party, wasn't it?",
      "date": new Date("2023-12-31T23:59:00.000Z")
    },
    "updatedAt": new Date("2023-12-31T23:59:00.000Z")
  },
  {
    "id": "chat4",
    "title": "Budget Meeting",
    "participants": [
      {
        "id": "user5",
        "name": "Edward Norton",
        "avatarUrl": "https://example.com/avatar/user5.png"
      },
      {
        "id": "user1",
        "name": "Alice Smith",
        "avatarUrl": "https://example.com/avatar/user1.png"
      }
    ],
    "lastMessage": {
      "id": "msg4",
      "senderId": "user1",
      "content": "I've attached the final budget report for this year.",
      "date": new Date("2023-12-30T11:30:00.000Z")
    },
    "updatedAt": new Date("2023-12-30T11:30:00.000Z")
  }
]

const presentChats: Chat[] = [
  {
    "id": "chat1",
    "title": "Project Discussion",
    "participants": [
      {
        "id": "user1",
        "name": "Alice Smith",
        "avatarUrl": "https://example.com/avatar/user1.png"
      },
      {
        "id": "user2",
        "name": "Bob Johnson",
        "avatarUrl": "https://example.com/avatar/user2.png"
      }
    ],
    "lastMessage": {
      "id": "msg1",
      "senderId": "user2",
      "content": "Hey, have you checked the latest project files?",
      "date": new Date("2024-03-01T09:20:14.000Z")
    },
    "updatedAt": new Date("2024-03-01T09:20:14.000Z")
  },
  {
    "id": "chat2",
    "title": "Coffee Break",
    "participants": [
      {
        "id": "user3",
        "name": "Charlie Davies",
        "avatarUrl": "https://example.com/avatar/user3.png"
      },
      {
        "id": "user1",
        "name": "Alice Smith",
        "avatarUrl": "https://example.com/avatar/user1.png"
      }
    ],
    "lastMessage": {
      "id": "msg2",
      "senderId": "user3",
      "content": "Don't forget we have a coffee break scheduled for 15:00.",
      "date": new Date("2024-03-01T08:45:00.000Z")
    },
    "updatedAt": new Date("2024-03-01T08:45:00.000Z")
  }
]
