import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';
import { ApplicationReferenceTableService } from './application-reference-table.service';

@Component({
  selector: 'jhi-application-reference-table-delete-dialog',
  templateUrl: './application-reference-table-delete-dialog.component.html',
})
export class ApplicationReferenceTableDeleteDialogComponent {
  applicationReferenceTable: IApplicationReferenceTable;

  constructor(
    protected applicationReferenceTableService: ApplicationReferenceTableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.applicationReferenceTableService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'applicationReferenceTableListModification',
        content: 'Deleted an applicationReferenceTable',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-application-reference-table-delete-popup',
  template: '',
})
export class ApplicationReferenceTableDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationReferenceTable }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApplicationReferenceTableDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static',
        });
        this.ngbModalRef.componentInstance.applicationReferenceTable = applicationReferenceTable;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/application-reference-table', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/application-reference-table', { outlets: { popup: null } }]);
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
