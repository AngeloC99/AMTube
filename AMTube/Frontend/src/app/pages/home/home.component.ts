import { Component, OnInit } from '@angular/core';
import {VideoService} from "../../services/video.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private videoService: VideoService) { }

  ngOnInit(): void {
  }

}
