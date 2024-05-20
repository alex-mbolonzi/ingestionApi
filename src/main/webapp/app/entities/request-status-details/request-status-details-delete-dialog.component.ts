import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequestStatusDetails } from 'app/shared/model/request-status-details.model';
import { RequestStatusDetailsService } from './request-status-details.service';

@Component({
  selector: 'jhi-request-status-details-delete-dialog',
  templateUrl: './request-status-details-delete-dialog.component.html',
})
export class RequestStatusDetailsDeleteDialogComponent {
  requestStatusDetails: IRequestStatusDetails;

  constructor(
    protected requestStatusDetailsService: RequestStatusDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.requestStatusDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'requestStatusDetailsListModification',
        content: 'Deleted an requestStatusDetails',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-request-status-details-delete-popup',
  template: '',
})
export class RequestStatusDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requestStatusDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RequestStatusDetailsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static',
        });
        this.ngbModalRef.componentInstance.requestStatusDetails = requestStatusDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/request-status-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/request-status-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
